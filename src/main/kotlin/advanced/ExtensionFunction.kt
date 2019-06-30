package advanced

fun <T> String.concatAsString(b: T) : String {
    return this.toString() + b.toString()
}

fun <T> T.someFunction(param1: T) : Int {
    return this.toString().toInt() + param1.toString().toInt()
}

operator fun List<Int>.times(by: Int): List<Int> {
    return this.map { it * by }
}

class ExtensionClass(){
    var int1 = 10
    var float1 = 20.0f
    var myString = ""

    fun SomeMethod(){
        //uzycie extension methods
        myString = myString.concatAsString(int1)
        float1.someFunction(int1)

        //uzycie nadpisania operatora
        listOf(1, 2, 3) * 4
    }
}
