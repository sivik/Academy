package coroutine

import kotlinx.coroutines.*

// async/await — uruchamianie współbieżnych zadań które ZWRACAJĄ wynik
// Różnica vs launch: launch zwraca Job (wynik ignorowany), async zwraca Deferred<T>
// Deferred<T> to obietnica (promise) — wynik odbieramy przez .await()

// ---- 1. Podstawowy async/await ----

suspend fun fetchUserName(id: Int): String {
    delay(1000)  // symulacja zapytania do API
    return "Użytkownik_$id"
}

suspend fun fetchUserScore(id: Int): Int {
    delay(800)  // symulacja zapytania do bazy danych
    return id * 100
}

suspend fun sequentialExample() {
    println("=== Sekwencyjne (wolne) ===")
    val start = System.currentTimeMillis()

    // Sekwencyjne — jedno po drugim, łącznie ~1800ms
    val name = fetchUserName(1)
    val score = fetchUserScore(1)

    println("$name: $score pkt (${System.currentTimeMillis() - start}ms)")
}

suspend fun parallelExample() = coroutineScope {
    println("=== Równoległe z async/await (szybkie) ===")
    val start = System.currentTimeMillis()

    // async uruchamia obie korutyny JEDNOCZEŚNIE
    val nameDeferred: Deferred<String> = async { fetchUserName(1) }
    val scoreDeferred: Deferred<Int> = async { fetchUserScore(1) }

    // await() czeka na wynik — ale obie już pracują równolegle
    val name = nameDeferred.await()
    val score = scoreDeferred.await()

    println("$name: $score pkt (${System.currentTimeMillis() - start}ms)")
    // Łącznie ~1000ms zamiast ~1800ms!
}

// ---- 2. asyncLazy — uruchom kiedy potrzebujesz ----

suspend fun lazyAsyncExample() = coroutineScope {
    println("=== async z LAZY start ===")

    // CoroutineStart.LAZY — korutyna NIE startuje od razu
    val lazyResult = async(start = CoroutineStart.LAZY) {
        println("  Teraz startuje lazy async")
        delay(500)
        "Wynik leniwy"
    }

    println("Przed await — korutyna jeszcze nie startowała")
    val result = lazyResult.await()  // dopiero teraz startuje
    println("Wynik: $result")
}

// ---- 3. awaitAll — czekanie na wiele Deferred naraz ----

suspend fun fetchData(id: Int): String {
    delay(200)
    return "dane_$id"
}

suspend fun awaitAllExample() = coroutineScope {
    println("=== awaitAll ===")
    val start = System.currentTimeMillis()

    // Tworzymy listę Deferred
    val deferreds = (1..5).map { id ->
        async { fetchData(id) }
    }

    // awaitAll czeka na wszystkie i zwraca listę wyników
    val results = awaitAll(*deferreds.toTypedArray())
    println("Wyniki: $results (${System.currentTimeMillis() - start}ms)")
    // ~200ms zamiast ~1000ms (5 x 200ms sekwencyjnie)
}

// ---- 4. Obsługa błędów w async ----

suspend fun riskyOperation(shouldFail: Boolean): String {
    delay(300)
    if (shouldFail) throw RuntimeException("Coś poszło nie tak!")
    return "Sukces"
}

suspend fun errorHandlingExample() {
    println("=== Obsługa błędów ===")

    // SupervisorScope — błąd w jednym async NIE anuluje pozostałych
    supervisorScope {
        val okDeferred = async { riskyOperation(false) }
        val failDeferred = async { riskyOperation(true) }

        // Obsługa błędu przy await
        val okResult = okDeferred.await()
        val failResult = try {
            failDeferred.await()
        } catch (e: RuntimeException) {
            "Błąd: ${e.message}"
        }

        println("OK: $okResult")
        println("Fail: $failResult")
    }
}

// ---- 5. Structured concurrency — Deferred respektuje scope ----

suspend fun structuredConcurrencyExample() {
    println("=== Structured concurrency ===")

    try {
        coroutineScope {  // anuluje wszystkie dzieci gdy jeden rzuci wyjątek
            val a = async {
                delay(100)
                println("  A gotowe")
                "wynik A"
            }
            val b = async {
                delay(50)
                throw CancellationException("B anulowane celowo")
            }

            println("Czekam na a: ${a.await()}")
            // b rzuciło wyjątek — cały scope zostaje anulowany
        }
    } catch (e: CancellationException) {
        println("Scope anulowany: ${e.message}")
    }
}

suspend fun main() {
    runBlocking {
        sequentialExample()
        println()
        parallelExample()
        println()
        lazyAsyncExample()
        println()
        awaitAllExample()
        println()
        errorHandlingExample()
        println()
        structuredConcurrencyExample()
    }
}
