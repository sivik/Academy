package coroutine

import kotlinx.coroutines.*

// Dispatcher — decyduje NA KTÓRYM wątku lub puli wątków korutyna się wykonuje
// To klucz do rozumienia jak korutyny współpracują z wątkami systemu

// ---- 1. Rodzaje Dispatcher'ów ----
// Dispatchers.Main    — główny wątek UI (Android/Swing). W zwykłym JVM niedostępny
// Dispatchers.IO      — pula wątków dla operacji blokujących (sieć, dysk, baza danych)
// Dispatchers.Default — pula wątków CPU (tyle co rdzenie procesora), obliczenia
// Dispatchers.Unconfined — nie przypisuje konkretnego wątku (ostrożnie!)

fun dispatchersBasicExample() {
    runBlocking {
        // Dispatchers.Default — operacje CPU-intensive
        val result = withContext(Dispatchers.Default) {
            println("Default: ${Thread.currentThread().name}")
            (1..1_000_000).sum()  // kosztowne obliczenia
        }
        println("Suma: $result")

        // Dispatchers.IO — operacje I/O (tu symulujemy)
        withContext(Dispatchers.IO) {
            println("IO: ${Thread.currentThread().name}")
            delay(100)  // symulacja czytania pliku
            println("Plik wczytany")
        }

        // Bez dispatchera — dziedziczy po runBlocking (main thread)
        println("Main: ${Thread.currentThread().name}")
    }
}

// ---- 2. withContext — przełączanie kontekstu w korutynie ----
// Najlepszy sposób na zmianę Dispatchera wewnątrz suspend funkcji

suspend fun loadFromDisk(): String {
    // Operacja blokująca — wykonaj na IO dispatcher, nie blokuj głównego wątku
    return withContext(Dispatchers.IO) {
        delay(200)  // symulacja odczytu z dysku
        "dane z dysku"
    }
}

suspend fun processData(data: String): String {
    // Kosztowne obliczenia — wykonaj na Default dispatcher
    return withContext(Dispatchers.Default) {
        data.uppercase().reversed()  // symulacja przetwarzania
    }
}

fun withContextExample() {
    runBlocking {
        println("=== withContext ===")
        val raw = loadFromDisk()
        val processed = processData(raw)
        println("Wynik: $processed")
    }
}

// ---- 3. Job — kontrola nad korutyną ----
// Job reprezentuje korutynę i pozwala na jej anulowanie, oczekiwanie itp.

fun jobExample() {
    runBlocking {
        println("=== Job ===")

        val job: Job = launch {
            repeat(10) { i ->
                println("  Krok $i")
                delay(300)
            }
        }

        delay(1000)
        println("Anulowanie...")
        job.cancel()      // wysyła sygnał anulowania
        job.join()        // czeka aż korutyna się zakończy
        println("Zakończone. isActive=${job.isActive}, isCancelled=${job.isCancelled}")
    }
}

// ---- 4. CoroutineScope — własny zakres życia ----
// Wszystkie korutyny w scope są powiązane — anulowanie scope anuluje wszystkie dzieci

class DataProcessor : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    private val jobs = mutableListOf<Job>()

    fun processAsync(data: String) {
        val job = launch {
            println("  Przetwarzam: $data (wątek: ${Thread.currentThread().name})")
            delay(500)
            println("  Gotowe: $data")
        }
        jobs.add(job)
    }

    suspend fun waitForAll() = jobs.forEach { it.join() }

    fun cancelAll() = cancel()  // anuluje cały scope (z CoroutineScope)
}

fun coroutineScopeExample() {
    runBlocking {
        println("=== CoroutineScope ===")
        val processor = DataProcessor()

        processor.processAsync("element-1")
        processor.processAsync("element-2")
        processor.processAsync("element-3")

        processor.waitForAll()
        println("Wszystko przetworzone")
    }
}

// ---- 5. SupervisorJob — izolacja błędów ----
// Normalny Job: błąd dziecka anuluje rodzica i wszystkie rodzeństwo
// SupervisorJob: błąd dziecka NIE wpływa na inne dzieci

fun supervisorJobExample() {
    runBlocking {
        println("=== SupervisorJob ===")

        val supervisor = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + supervisor)

        val job1 = scope.launch {
            delay(100)
            println("  Job1: rzucam wyjątek!")
            throw RuntimeException("Błąd w Job1")
        }

        val job2 = scope.launch {
            delay(300)
            println("  Job2: zakończony pomyślnie")  // wykona się mimo błędu w Job1!
        }

        // CoroutineExceptionHandler — obsługa nieobsłużonych wyjątków
        val handler = CoroutineExceptionHandler { _, exception ->
            println("  Handler: złapano ${exception.message}")
        }

        val job3 = scope.launch(handler) {
            delay(100)
            throw IllegalStateException("Błąd w Job3")
        }

        joinAll(job1, job2, job3)
        supervisor.cancel()
        println("Koniec")
    }
}

// ---- 6. Anulowanie i isActive / ensureActive ----

fun cancellationExample() {
    runBlocking {
        println("=== Anulowanie ===")

        val job = launch(Dispatchers.Default) {
            // Korutyna musi sprawdzać czy jest aktywna w długich obliczeniach
            var result = 0L
            for (i in 1..1_000_000) {
                ensureActive()  // rzuca CancellationException gdy anulowano
                result += i
            }
            println("  Wynik: $result")  // nie dotrze tu po anulowaniu
        }

        delay(50)
        job.cancel()
        job.join()
        println("Korutyna anulowana")
    }
}

fun main() {
    println("=== 1. Podstawy Dispatchers ===")
    dispatchersBasicExample()

    println("\n=== 2. withContext ===")
    withContextExample()

    println("\n=== 3. Job ===")
    jobExample()

    println("\n=== 4. CoroutineScope ===")
    coroutineScopeExample()

    println("\n=== 5. SupervisorJob ===")
    supervisorJobExample()

    println("\n=== 6. Anulowanie ===")
    cancellationExample()
}
