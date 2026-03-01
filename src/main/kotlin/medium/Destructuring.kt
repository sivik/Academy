package medium

// Destrukturyzacja (Destructuring Declarations) — rozpakowywanie obiektów na zmienne
// Działa dzięki funkcjom component1(), component2(), ... generowanym automatycznie
// przez data class lub implementowanym ręcznie

// ---- 1. Destrukturyzacja data class ----

data class Point(val x: Int, val y: Int)

data class Person(val name: String, val age: Int, val city: String)

fun dataClassDestructuring() {
    val point = Point(10, 20)

    // Zamiast: val x = point.x; val y = point.y
    val (x, y) = point
    println("x=$x, y=$y")

    val person = Person("Anna", 30, "Kraków")
    val (name, age, city) = person
    println("$name ma $age lat, mieszka w $city")

    // Pomijanie pól — użyj _ dla pól których nie potrzebujesz
    val (onlyName, _, onlyCity) = person
    println("$onlyName z $onlyCity")
}

// ---- 2. Destrukturyzacja w pętli for ----

fun destructuringInLoops() {
    val people = listOf(
        Person("Jan", 25, "Warszawa"),
        Person("Maria", 32, "Gdańsk"),
        Person("Piotr", 28, "Wrocław")
    )

    // Destrukturyzacja bezpośrednio w nagłówku pętli
    for ((name, age) in people) {
        println("$name: $age lat")
    }

    // Destrukturyzacja mapy
    val capitals = mapOf("Polska" to "Warszawa", "Niemcy" to "Berlin", "Francja" to "Paryż")

    for ((country, capital) in capitals) {
        println("Stolica $country to $capital")
    }
}

// ---- 3. Destrukturyzacja Pair i Triple ----

fun pairAndTripleDestructuring() {
    val coordinates = Pair(52.23, 21.01)
    val (lat, lon) = coordinates
    println("Szerokość: $lat, Długość: $lon")

    // to — skrót tworzący Pair
    val (key, value) = "klucz" to "wartość"
    println("$key -> $value")

    val rgb = Triple(255, 128, 0)
    val (red, green, blue) = rgb
    println("RGB: R=$red G=$green B=$blue")
}

// ---- 4. Destrukturyzacja w lambdach ----

fun destructuringInLambdas() {
    val points = listOf(Point(1, 2), Point(3, 4), Point(5, 6))

    // Destrukturyzacja parametru lambdy
    points.forEach { (x, y) ->
        println("Punkt: ($x, $y)")
    }

    // Z indeksem
    points.forEachIndexed { index, (x, y) ->
        println("Punkt[$index]: ($x, $y)")
    }

    // W map
    val distances = points.map { (x, y) -> Math.sqrt((x * x + y * y).toDouble()) }
    println("Odległości od (0,0): $distances")

    // Destrukturyzacja wpisów mapy w lambdzie
    val scores = mapOf("Alicja" to 95, "Bartek" to 87, "Celina" to 92)
    val topStudents = scores.filter { (_, score) -> score >= 90 }
    println("Najlepsi: $topStudents")
}

// ---- 5. Własne componentN() — destrukturyzacja bez data class ----

class Color(val r: Int, val g: Int, val b: Int) {
    // Ręczna implementacja operatorów destrukturyzacji
    operator fun component1() = r
    operator fun component2() = g
    operator fun component3() = b
}

fun customComponentFunctions() {
    val color = Color(100, 200, 50)
    val (r, g, b) = color
    println("Kolor: R=$r, G=$g, B=$b")
}

// ---- 6. Destrukturyzacja w instrukcji when ----

sealed class Result<out T>
data class Success<T>(val data: T, val code: Int) : Result<T>()
data class Failure(val error: String, val code: Int) : Result<Nothing>()

fun handleResult(result: Result<String>) {
    when (result) {
        is Success -> {
            val (data, code) = result
            println("Sukces [$code]: $data")
        }
        is Failure -> {
            val (error, code) = result
            println("Błąd [$code]: $error")
        }
    }
}

fun whenDestructuringExample() {
    handleResult(Success("Dane załadowane", 200))
    handleResult(Failure("Brak dostępu", 403))
}

fun main() {
    println("=== 1. Data class ===")
    dataClassDestructuring()

    println("\n=== 2. Pętle ===")
    destructuringInLoops()

    println("\n=== 3. Pair i Triple ===")
    pairAndTripleDestructuring()

    println("\n=== 4. Lambdy ===")
    destructuringInLambdas()

    println("\n=== 5. Własne componentN() ===")
    customComponentFunctions()

    println("\n=== 6. When ===")
    whenDestructuringExample()
}
