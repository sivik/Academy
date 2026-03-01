package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Flow — strumień wartości emitowanych asynchronicznie
// Analogia: jak Sequence, ale asynchroniczna i suspendable
// Cold flow — nie produkuje danych dopóki ktoś nie zaczyna nasłuchiwać (collect)

// ---- 1. Podstawowy Flow ----

fun simpleFlow(): Flow<Int> = flow {
    println("  Flow startuje")
    for (i in 1..5) {
        delay(200)
        println("  Emituję: $i")
        emit(i)  // wysłanie wartości do zbieracza (collector)
    }
    println("  Flow kończy")
}

fun basicFlowExample() {
    runBlocking {
        println("=== Podstawowy Flow ===")
        println("Przed collect")
        simpleFlow().collect { value ->
            println("  Odebrano: $value")
        }
        println("Po collect")
    }
}

// ---- 2. Operatory na Flow ----
// Flow ma podobne operatory co kolekcje: map, filter, take itp.
// Ale są lazy i asynchroniczne!

fun flowOperatorsExample() {
    runBlocking {
        println("=== Operatory Flow ===")

        flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter { it % 2 == 0 }           // tylko parzyste
            .map { it * it }                   // do kwadratu
            .take(3)                           // tylko pierwsze 3
            .collect { println("  $it") }      // 4, 16, 36

        println()

        // transform — bardziej elastyczny niż map (można emitować wiele razy)
        flowOf("a", "b", "c")
            .transform { letter ->
                emit("$letter (małe)")
                emit("${letter.uppercase()} (duże)")
            }
            .collect { println("  $it") }
    }
}

// ---- 3. Różne sposoby tworzenia Flow ----

fun flowCreationExample() {
    runBlocking {
        println("=== Tworzenie Flow ===")

        // flowOf — z konkretnych wartości
        flowOf(1, 2, 3).collect { print("$it ") }
        println()

        // asFlow() — z kolekcji lub zakresu
        listOf("A", "B", "C").asFlow().collect { print("$it ") }
        println()
        (1..5).asFlow().collect { print("$it ") }
        println()

        // callbackFlow — integracja z callback API (np. Android listeners)
        val callbackFlow = callbackFlow<String> {
            // Tu podłączyłbyś listener, np. sensor, socket, itp.
            trySend("Wartość 1")
            trySend("Wartość 2")
            close()  // zamknij kanał gdy gotowe
            awaitClose { /* sprzątanie: odłącz listener */ }
        }
        callbackFlow.collect { print("$it ") }
        println()
    }
}

// ---- 4. Terminal operators ----
// collect, toList, first, last, count, reduce, fold...

fun terminalOperatorsExample() {
    runBlocking {
        println("=== Terminal operators ===")

        val numbers = (1..10).asFlow()

        println("toList: ${numbers.toList()}")
        println("first: ${numbers.first()}")
        println("first { >5 }: ${numbers.first { it > 5 }}")
        println("count: ${numbers.count()}")
        println("reduce: ${numbers.reduce { acc, value -> acc + value }}")
        println("fold: ${numbers.fold(100) { acc, value -> acc + value }}")
    }
}

// ---- 5. StateFlow — stan reaktywny ----
// Hot flow — produkuje dane niezależnie od subskrybentów
// Zawsze ma wartość. Nowy subskrybent dostaje AKTUALNĄ wartość natychmiast.
// Idealny do trzymania stanu w ViewModel (Android)

class CounterViewModel {
    // MutableStateFlow — wewnętrzny, zapisywalny
    private val _counter = MutableStateFlow(0)
    // StateFlow — zewnętrzny, tylko do odczytu
    val counter: StateFlow<Int> = _counter.asStateFlow()

    fun increment() { _counter.value++ }
    fun decrement() { _counter.value-- }
    fun reset() { _counter.value = 0 }
}

fun stateFlowExample() {
    runBlocking {
        println("=== StateFlow ===")

        val viewModel = CounterViewModel()

        // Subskrybent reaguje na zmiany stanu
        val job = launch {
            viewModel.counter.collect { value ->
                println("  UI aktualizacja: counter = $value")
            }
        }

        delay(100)
        viewModel.increment()
        viewModel.increment()
        viewModel.increment()
        viewModel.decrement()
        viewModel.reset()
        delay(100)

        job.cancel()
    }
}

// ---- 6. SharedFlow — zdarzenia (events) ----
// Hot flow — może mieć wielu subskrybentów
// Nowy subskrybent NIE dostaje historycznych wartości (chyba że skonfigurujemy replay)
// Idealny do zdarzeń jednorazowych (nawigacja, komunikaty błędów)

class EventBus {
    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events.asSharedFlow()

    suspend fun publish(event: String) = _events.emit(event)
}

fun sharedFlowExample() {
    runBlocking {
        println("=== SharedFlow ===")

        val bus = EventBus()

        // Subskrybent 1
        val subscriber1 = launch {
            bus.events.collect { event ->
                println("  Subskrybent1: $event")
            }
        }

        // Subskrybent 2
        val subscriber2 = launch {
            bus.events.collect { event ->
                println("  Subskrybent2: $event")
            }
        }

        delay(100)
        bus.publish("Zdarzenie A")
        bus.publish("Zdarzenie B")
        delay(100)

        subscriber1.cancel()
        subscriber2.cancel()
    }
}

// ---- 7. Obsługa błędów w Flow ----

fun errorHandlingFlowExample() {
    runBlocking {
        println("=== Obsługa błędów ===")

        flow {
            emit(1)
            emit(2)
            throw RuntimeException("Błąd w flow!")
            emit(3)  // nigdy nie dotrze
        }
            .catch { e -> emit(-1).also { println("  catch: ${e.message}") } }
            .onCompletion { error ->
                if (error == null) println("  Flow zakończony pomyślnie")
                else println("  Flow zakończony z błędem: ${error.message}")
            }
            .collect { println("  Wartość: $it") }
    }
}

fun main() {
    println("=== 1. Podstawowy Flow ===")
    basicFlowExample()

    println("\n=== 2. Operatory ===")
    flowOperatorsExample()

    println("\n=== 3. Tworzenie Flow ===")
    flowCreationExample()

    println("\n=== 4. Terminal operators ===")
    terminalOperatorsExample()

    println("\n=== 5. StateFlow ===")
    stateFlowExample()

    println("\n=== 6. SharedFlow ===")
    sharedFlowExample()

    println("\n=== 7. Obsługa błędów ===")
    errorHandlingFlowExample()
}
