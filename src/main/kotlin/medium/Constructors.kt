package medium

open class Parent {
    private val a = println("Parent.a")

    constructor(arg: Unit = println("Parent - inicjalizacja parametru w konstruktorze arg")) {
        println("Parent główny constructor (primary constructor)")
    }

    init {
        println("Parent.init")
    }

    private val b = println("Parent.b")
}

class Child : Parent {
    val a = println("Child.a")

    init {
        println("Child.init 1")
    }

    constructor(arg: Unit = println("Child - inicjalizacja parametru w konstruktorze arg")) : super() {
        println("Child primary constructor")
    }

    val b = println("Child.b")

    constructor(
        arg: Int,
        arg2: Unit = println("Child drugi konstruktor -  inicjalizacja parametru arg2 w drugim konstruktorze")
    ) : this() {
        println("Child secondary constructor")
    }

    init {
        println("Child.init 2")
    }
}

fun main() {
    Child(1)
    /*
    Child drugi konstruktor -  inicjalizacja parametru arg2 w drugim konstruktorze
    Child - inicjalizacja parametru w konstruktorze arg
    Parent - inicjalizacja parametru w konstruktorze arg
    Parent.a
    Parent.init
    Parent.b
    Parent główny constructor (primary constructor)
    Child.a
    Child.init 1
    Child.b
    Child.init 2
    Child primary constructor
    Child secondary constructor
     */
}

//Konstruktor po nazwie klasy
open class Uncle(arg: Long?, arg2: String?) {
    private var argument: Long? = null
    private var argument2: String? = null

    init {
        argument = arg
        argument2 = arg2
    }
}