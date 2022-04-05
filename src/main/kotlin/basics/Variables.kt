package basics

fun fooVariables() {
    /*DWA NAJWAŻNIEJSZE SŁOWA PODCZAS DEFINIOWANIA/DEKLAROWANIA ZMIENNYCH*/
    // "val" - zmienna, która jest niezmienna
    // "var" - zmienna, zmienna, która jest zmienna

    /*Typy zmiennych nie pozwalających na wpisanie wartości null oraz ich definicja*/
    val variableInt: Int
    var variableFloat: Float
    var variableLong: Long
    val variableDouble: Double
    var variableString: String
    val variableChar: Char
    val variableBoolean: Boolean

    //variableInt = null

    //Należy stosować ten typ z głową! W przypadku niejawnego przypisania wartości null do typu non-null nastapi
    //rzucenie wyjatku i koniec programu

    /*Typy zmiennych nie pozwalających na wpisanie wartości null oraz ich definicja*/
    var variableIntWithNull: Int?
    var variableFloatWithNull: Float?
    val variableLongWithNull: Long?
    val variableDoubleWithNull: Double?
    var variableStringWithNull: String?
    val variableCharWithNull: Char?
    val variableBooleanWithNull: Boolean?

    variableDoubleWithNull = null

    /*Deklaracja zmiennej*/
    val myInt: Int = 7
    val myString: String = "13455"

    /* Skrócony zapis deklaracji -> kompilator sam się domysli */
    val mySecondString = "Jestem skrocona deklaracja"
    var mySecondInt = 9999
    val myNull = null

    /*NUllable != non-nullable. */
    //variableString = variableStringWithNull

    //Jesli chcemy w przypadku wystapienia wartosci null dac jakas domyslna wartosc lub !!
    //variableString = variableStringWithNull !!

    //val result = variableDoubleWithNull ?: 88.9


    /*Dodatkowe typy o których warto wiedzieć*/
    val big: Any  //jest to jak Object w C# lub Java
    val number: Number
}

//Zmienne Globalne
var mutableGlobalVariable: String = "Hej, jestem zmienna globalna, widać mnie wszedzie. Jako zmienna \"zmienna\" mozesz mnie zmienić"
val immutableGlobalVariable: String = "Hej, jestem zmienna globalna, widać mnie wszedzie. Jako zmienna \"niezmienna\" nie mozesz mnie zmienić"
const val constVariable = "Hej, a  ja jestem stałą. Trzeba mnie od razu zainicjalizować"

//Roznica miedzy val, a const val polega na tym, ze const val musi byc domyslnie od razu zadeklarowana
//Natomiast val "czeka" na swoja deklaracje

//Przeslonienie nazw jak w innych jezykach
class VariableClass(){
    //Pola - nazwy składowych klasy
    val myfield = 7
    var myFieldString = "10"
    //var mySecondFieldString: String
    //val mySecondFieldInt: Int?

    lateinit var myLateVariable: String
    var mySecondLateVariable: String? = null


    fun x(){
        if(::myLateVariable.isInitialized){

        }
    }

    // https://stackoverflow.com/a/36623703

    class BigClass()
    private val bigObject: BigClass = BigClass()

    //read only property ->
    /*
    *  jest używana głównie wtedy, gdy chcemy uzyskać dostęp do jakiejś
    * właściwości tylko do odczytu, ponieważ przez cały czas ma miejsce dostęp do tego samego obiektu.*/
    val heavyObject: BigClass by lazy {
        BigClass()
    }


}

//Nalezy pamietaz ze przy delegacie lazy uzywamy tego samego obiektu
fun main(){
    val someClass = VariableClass()
    println(someClass.heavyObject)
    println(someClass.heavyObject)
}


//By lazy pozwala zainicjować zmienną dopiero wtedy gdy bedzie ona nam potrzebna
//Widac podejscie ze ma uzupelnic zmienna po sprawdzeniu czy jest null i wtedy ja uzupelnic.
class Printer(val model: String, val type: String) {

    var fullName: String? = null

    fun printName(){
        if (fullName == null){
            fullName = "$model $type"
        }
        println(fullName)
    }
}

//Mozna to zrobic prosciej
class Phone (val model: String, val type: String) {
    //Wartość niezmienna
    val fullName: String by lazy { "$model $type" }

    fun printName(){
        println(fullName)
    }
}