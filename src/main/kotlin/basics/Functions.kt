package basics

//Fukcja przyjmujaca dwa paramtry zwracająca typ void
fun GlobalFunction(param1: String, param2: Long?) {}

//Funkcja zwracajaca typ string
fun GlobalFunction(): String {
    val string1 = "1"
    val string2 = "2"
    return string1 + string2
}

//Funkcja z zapisem skrótowym
fun GlobalFunction2(): String {
    return "MyfunctionString"
}

fun GlobalFunction2Short() = "MyfunctionString"

//Funckja z zapisem skrótowym
fun GlobalFunction3(): String = GlobalFunction2()

fun GlobalFunction3Short() = GlobalFunction2()


class FunctionClass() {
    //metody
    fun methodOne(param1: String?, param2: String?){}
    fun methodTwo(param1: String?, param2: String?):Int{return 7}
    fun methodThree(param1: String?, param2: String?):Int? = 7
    fun methodFour(param1: String?, param2: String?):Int? = null
    fun methodFive(param1: String?, param2: String?):Int? {return null}
    fun methodSix(param1: String?, param2: String?):Int? = methodFive(param1,param2)
    fun methodSeven(param1: String?, param2: String?) = methodFive(param1,param2)
}