package medium

//domyslnie zasieg jest public, występują także protected,internal, oraz private
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
