package advanced

class Person(
    private val firstName: String,
    private val surname: String) {
    fun printMe() = println { "my first name $firstName and surname $surname" }

    private val interests: MutableList<String> = mutableListOf()

    fun addInterest(interes: String) = interests.add(interes)
    infix fun addInfixInterst(interes: String) = interests.add(interes)
}

fun main() {
    val nate = Person("Nate", "Ebel")
    nate.addInterest("Kotlin")
    nate addInfixInterst "C++"
}