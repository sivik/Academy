package notassignyet

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