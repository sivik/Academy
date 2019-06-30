package medium

//domyslnie zasieg jest public, występują także protected oraz privare
class IntrodctionClass {
    //pola
    val prop = 15

    //właściwości
    var name: String = "Sebek"
        get() = field
        set(value) {
            field = value
        }

    //metody
    fun myClassMethod(){}

}

abstract class IntroductionAbstractClass{
    lateinit var x: String
    var p = 10
    abstract fun myAbstractMethod()

    fun mySecondAbstractMethod(){
        val x =10
        mySecondAbstractMethod()
    }
}

open class IntroductionOpenClass(){}

interface IntroductionInterface{
    fun myInterfaceMethod()
}

interface IntroductionSecondInterface{
    fun fooInterfaceMethod()
    fun myInterfaceMethod(){
        val x = 9
        fooInterfaceMethod()
    }
}

//-------------------------------------------------------------------

class TestClass(): IntroductionOpenClass()

class TestClass2(): IntroductionInterface,IntroductionSecondInterface {
    override fun myInterfaceMethod() {
    }
    override fun fooInterfaceMethod() {
    }
}

class TestClass3(): IntroductionAbstractClass() {
    override fun myAbstractMethod() {
       print("abstract method")
    }

    fun classMethod(){
        mySecondAbstractMethod()
    }
}

//class TestClass4: IntrodctionClass()  -> Bez open nie podziedziczymy

//--------------------------------------------------------------------------

sealed class IntroductionSealedClass
// Nested
//sealed class Fruit() {
//    class Apple() : Fruit()
//    class Orange() : Fruit()
//}
//sealed class Fruit
//// Not nested
//class Apple() : Fruit()
//class Orange() : Fruit()
//// Fruits.kt
//sealed class Fruit() {
//    class Apple() : Fruit()
//    class Orange() : Fruit()
//    open class UnknownFruit(): Fruit()
//}
//// SomeOtherFile.kt
//class Grape : Fruit() // Not Acceptable
//class Tomato : UnknownFruit() // Acceptable