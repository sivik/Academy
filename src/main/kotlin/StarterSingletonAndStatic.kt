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

fun omgPlSNoStatic(){
    StarterSingletonAndStatic.stringSingleton
    StarterSingletonAndStatic.staticSingletonFunction()
    MyClass.stringClass
    MyClass.staticFunctionInCompanionObject()
}