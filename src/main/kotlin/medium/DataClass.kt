package medium

//Dobrze nadają się do modeli, tworzą wlasne equals i hashcode
data class ExampleDataClass(
    val variable1: String,
    val variable2: Long = 35L,
    val variable3: String?
) { /*Some code*/ }

data class SecondDataClass(
    val variable1: String? = null,
    val variable2: Long? = null,
    val variable3: String? = null
) { /*Some code*/ }

class ExampleClass {
    var someProperty = 10
    var secondDataClass = SecondDataClass()
    lateinit var exampleDataClass: ExampleDataClass
}

fun exampleMethod() {

    //Tworzenie obiektu
    val exampleObject = ExampleClass()

    //Tworzenie obiektu z uzyciem konstruktora
    val example2Object = ExampleDataClass("example", 20L, "null")

    val valueFromOther = 3456L
    val valueFromOther2 = "value from other"
    val valueFromOther3 = "value from other"

    //Inicjalizacja pol obiektu

    exampleObject.apply {
        someProperty = 1000

        //inicjalizacja dla wszystkich pol
        exampleDataClass = ExampleDataClass(
            valueFromOther2, valueFromOther, valueFromOther3
        )

        //inicjalizacja dla poł nullable
        secondDataClass = SecondDataClass(
            variable1 = valueFromOther2,
            variable3 = valueFromOther3
        )
    }
}