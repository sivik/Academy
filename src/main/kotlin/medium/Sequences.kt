package medium

// Sekwencje (Sequence) — leniwa (lazy) alternatywa dla kolekcji
// Lista przetwarza każdą operację na WSZYSTKICH elementach naraz (eager)
// Sekwencja przetwarza element PO elemencie przez cały pipeline (lazy)

// ---- 1. Różnica między List a Sequence ----

fun listVsSequenceExample() {
    println("=== List (eager) ===")
    // Każda operacja tworzy nową, tymczasową listę
    val resultList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .map { value ->
            println("  map($value)")
            value * 2
        }
        .filter { value ->
            println("  filter($value)")
            value > 10
        }
        .first()
    println("Wynik: $resultList")

    println("\n=== Sequence (lazy) ===")
    // Element przechodzi przez cały pipeline zanim następny zostanie przetworzony
    // Zatrzymuje się gdy znajdzie pierwszy pasujący element!
    val resultSeq = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .asSequence()  // konwersja listy na sekwencję
        .map { value ->
            println("  map($value)")
            value * 2
        }
        .filter { value ->
            println("  filter($value)")
            value > 10
        }
        .first()
    println("Wynik: $resultSeq")
    // Sekwencja przetworzyła tylko 6 elementów zamiast 10!
}

// ---- 2. generateSequence — nieskończone sekwencje ----

fun generateSequenceExample() {
    // Sekwencja liczb Fibonacciego — potencjalnie nieskończona
    val fibonacci = generateSequence(Pair(0, 1)) { (a, b) ->
        Pair(b, a + b)
    }.map { it.first }

    println("Pierwsze 10 liczb Fibonacciego:")
    println(fibonacci.take(10).toList())
    // [0, 1, 1, 2, 3, 5, 8, 13, 21, 34]

    // Sekwencja kolejnych potęg 2
    val powersOf2 = generateSequence(1) { it * 2 }
    println("\nPierwsze 8 potęg 2:")
    println(powersOf2.take(8).toList())
    // [1, 2, 4, 8, 16, 32, 64, 128]

    // generateSequence kończy się gdy lambda zwróci null
    val countdown = generateSequence(10) { n ->
        if (n > 1) n - 1 else null  // null kończy sekwencję
    }
    println("\nOdliczanie:")
    println(countdown.toList())
}

// ---- 3. sequence { } — builder z yield ----

fun sequenceBuilderExample() {
    // Budowanie sekwencji krok po kroku z użyciem yield/yieldAll
    val customSequence = sequence {
        println("Produkuję 1")
        yield(1)       // zawiesza i oddaje wartość

        println("Produkuję 2, 3")
        yieldAll(listOf(2, 3))  // oddaje wiele wartości naraz

        println("Produkuję 4")
        yield(4)

        // Kod tutaj nie wykona się dopóki konsument nie zażąda kolejnego elementu
        println("Produkuję 5")
        yield(5)
    }

    println("Biorę pierwsze 3 elementy:")
    customSequence.take(3).forEach { println("  Odebrano: $it") }
    // Produkuje tylko tyle ile potrzeba — "Produkuję 4" się nie wykona!
}

// ---- 4. Kiedy używać sekwencji, a kiedy list? ----

fun whenToUseSequences() {
    val numbers = (1..1_000_000).toList()

    // LISTA — lepiej gdy: mała kolekcja, wielokrotny dostęp, potrzebujemy indeksowania
    val listResult = numbers
        .filter { it % 2 == 0 }
        .map { it * 3 }
        .take(5)
        .toList()

    // SEKWENCJA — lepiej gdy: duża kolekcja, wiele operacji, short-circuiting (first/any/find)
    val seqResult = numbers
        .asSequence()
        .filter { it % 2 == 0 }
        .map { it * 3 }
        .take(5)
        .toList()

    println("Lista: $listResult")
    println("Sekwencja: $seqResult")
    // Wynik identyczny, ale sekwencja przetworzyła o wiele mniej elementów!

    // UWAGA: terminalne operacje (toList, first, count, sum) materializują sekwencję
    // Pośrednie operacje (map, filter, take) są leniwe
}

// ---- 5. Użyteczne operacje na sekwencjach ----

fun sequenceOperationsExample() {
    val sequence = sequenceOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5)

    println("chunked(3): ${sequence.chunked(3).toList()}")
    // [[3, 1, 4], [1, 5, 9], [2, 6, 5], [3, 5]]

    println("windowed(3): ${sequenceOf(1,2,3,4,5).windowed(3).toList()}")
    // [[1, 2, 3], [2, 3, 4], [3, 4, 5]]

    println("zipWithNext: ${sequenceOf(1,2,3,4).zipWithNext().toList()}")
    // [(1, 2), (2, 3), (3, 4)]

    println("distinct: ${sequence.distinct().toList()}")

    println("sorted: ${sequence.sorted().toList()}")
}

fun main() {
    println("=== 1. List vs Sequence ===")
    listVsSequenceExample()

    println("\n=== 2. generateSequence ===")
    generateSequenceExample()

    println("\n=== 3. Sequence Builder ===")
    sequenceBuilderExample()

    println("\n=== 4. Kiedy używać sekwencji ===")
    whenToUseSequences()

    println("\n=== 5. Operacje na sekwencjach ===")
    sequenceOperationsExample()
}
