package medium

enum class Size {
    SMALL, MEDIUM, BIG
}

data class Stone(
    val size: Size,
    val color: String,
    val type: String,
    val weight: Int
){
    override fun toString(): String {
       return "$color $type $weight"
    }
}

class Cookie(
    val size: Size,
    val weight: Int
)

fun mapSomething() {
    val stones = listOf(
        Stone(Size.BIG, "Red", "Ruby", 6),
        Stone(Size.MEDIUM, "Green", "Emerald", 4),
        Stone(Size.SMALL, "Blue", "Sapphire", 2)
    )

    val cookies = ArrayList<Cookie>()
    stones.forEach { stone ->
        val cookie = Cookie(stone.size, stone.weight)
        cookies.add(cookie)
    }

    val fastCookies = stones.map { stone -> Cookie(stone.size, stone.weight) }

    val colors = stones.map { it.color }

    val x = stones.mapIndexed { index, stone -> }
    val y = stones.forEachIndexed { index, stone -> }

}

fun flatMapSth() {
    data class Data(val items: List<String>)

    val data = listOf(
        Data(listOf("a", "b", "c")),
        Data(listOf("1", "2", "3"))
    )

    val combinedMap = data.map { it.items }
    //combinedMap -> [[a, b, c], [1, 2, 3]]

    val combined = data.flatMap { it.items }
    //combined -> [a, b, c, 1, 2, 3]

}

fun filterSth() {
    val stones = listOf(
        Stone(Size.BIG, "Red", "Ruby", 6),
        Stone(Size.MEDIUM, "Green", "Emerald", 4),
        Stone(Size.SMALL, "Blue", "Sapphire", 2)
    )
    val filteredStones = stones.filter { it.color == "Red" }

    val everything = listOf<Any>(1, "2", 3L, 4)

    val filteredInt = everything.filterIsInstance<Int>() // 1, 4
}

fun firstSth(){
    val stones = listOf(
        Stone(Size.BIG, "Red", "Ruby", 6),
        Stone(Size.BIG, "Green", "Emerald", 4),
        Stone(Size.SMALL, "Blue", "Sapphire", 2)
    )
    val firstStone = stones.first()
    val lastStone = stones.last()
    val firstWithStatement = stones.first { stone -> stone.size == Size.BIG } //w przypadku niedopasowania rzuca exception
    // Red Ruby 6
    val firstWithStatementOrNull = stones.firstOrNull { stone -> stone.size == Size.BIG } // w przypadku niedopasowania zwraca null
    // Red Ruby 6
    val lastWithStatement = stones.last { stone -> stone.size == Size.BIG } //w przypadku niedopasowania rzuca exception
    //Green Emerald 4
    val lastWithStatementOrNull = stones.lastOrNull { stone -> stone.size == Size.BIG } // w przypadku niedopasowania zwraca null
    //Green Emerald 4
}


fun sortSth(){
    val stones = listOf(
        Stone(Size.BIG, "Red", "Ruby", 6),
        Stone(Size.BIG, "Transparent", "Diamond", 1),
        Stone(Size.BIG, "Green", "Emerald", 4),
        Stone(Size.SMALL, "Blue", "Sapphire", 2)
    )
}

fun additionalExt(){
    //Equals
    val listA = listOf(13, 15, 27, -6)
    val listB = listOf(13, 27, -6, 15)
    println(listA == listB)
    //
    //"indexOf" ma na celu znaleźć dla nas miejsce na liście jakie zajmuje szukany przez nas obiekt.
    // Zwraca liczbę całkowitą w postaci indeksu na którym "leży" znaleziony obiekt
    // (-1, jeśli w ogóle takiego nie znaleziono).
    println(listA.indexOf(15))

    //sortowanie i odwrocenie elementow w liscie
    println(listA.sorted())
    println(listA.reversed())

}

fun contains(){
    val list = arrayListOf(1,2,3,4)
    println(list.contains(3))
    println(3 in list)

    println(list.contains(3).not())
    println(3 !in list)
}


fun main(){
    firstSth()
}
