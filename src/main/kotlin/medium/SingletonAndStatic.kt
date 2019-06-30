package medium

object StarterSingletonAndStatic {
    val stringSingleton = "Statyczna zmienna z Singletona"

    fun staticSingletonFunction():String{
        return "Jestem statyczna funkcja z Singletona"
    }
}

class MyClass{
    companion object{
        val stringClass = "Statyczna zmienna z klasy"
        //Wszystko tutaj jest statyczne
        fun staticFunctionInCompanionObject() = "Jestem statyczna funkcja z klasy"
    }
}

fun `OMG! Please NoStatic`(){
    println(StarterSingletonAndStatic.stringSingleton)
    println(StarterSingletonAndStatic.staticSingletonFunction())
    println(MyClass.stringClass)
    println(MyClass.staticFunctionInCompanionObject())
}

fun main(){
  `OMG! Please NoStatic`()
}