package advanced

// Metoda reduce() przekształca podaną kolekcję w pojedynczy wynik. Wykorzystuje ona operator funkcji lambda do
// połączenia pary elementów w tak zwaną wartość skumulowaną.
// Metoda ta następnie przemierza kolekcję od lewej do prawej i stopniowo
// łączy wartość skumulowaną z następnym elementem.

fun reduce(){
    val numbers: List<Int> = listOf(1, 2, 3)
    val sum: Int = numbers.reduce { acc, next -> acc + next }
    println(sum) // 6 czyli 1 + 2 + 3

    //gdy lista jest pusta zostanie rzucony exception
    val emptyList = listOf<Int>()
    emptyList.reduce { acc, next -> acc + next } // Throws<RuntimeException>


    //Blad kompilacji poniewaz drugi podtyp musi byc podtypem pierwszego - wynika to z definicji funkcji reduce
    //val sum2: Long = numbers.reduce<Long, Int> { acc, next -> acc.toLong() + next.toLong() }
    //val sum2: Long = numbers.reduce<Long, Number> { acc, next -> acc.toLong() + next.toLong() }
}

fun fold(){
    val numbers: List<Int> = listOf(1, 2, 3)
    val sum: Int = numbers.fold(0){ acc, next -> acc + next }
    println(sum) // 6 czyli 1 + 2 + 3


    //gdy lista jest pusta zostanie zwrocona wartosc z parametru initial
    val emptyList = listOf<Int>()
    emptyList.fold(0) { acc, next -> acc + next }

    val suma: Long = numbers.fold(0L){ acc, next -> acc + next.toLong() }
    println(suma) //6L

    //--------------------------------------------------------------
    val (even, odd) = numbers.fold(Pair(mutableListOf<Int>(), mutableListOf<Int>())) { eoPair, number ->
        eoPair.apply {
            when (number % 2) {
                0 -> first += number
                else -> second += number
            }
        }
    }

    println(even) // -> list(2)
    println(odd) // -> list(1,3)
}