package medium

typealias MyExtraProvider = (() -> Char)?

class FakeClass {
    val functionProvider: () -> String = { "Something" }
    var functionProvider2: () -> String = { "Something2" }
    var functionProvider3: (() -> String)? = null
    var functionProvider4: MyExtraProvider = null

    fun print() {
        //dla val mamy jedna funkcje ktora jest na pocatku stalana
        val something = functionProvider.invoke()
        //w przypadku var mozemy srodek funkcji nadpisywac
        functionProvider2 = { "something new" }
        val something2 = functionProvider2.invoke()
        println(something)
        println(something2)

        //wywolanie funkcji providera w lambdzie
        functionProvider3?.let {
            it()
        }
    }
}

class Greton {
    private val list = listOf("x", "y", "z")
    fun printX(checkX: (String) -> Boolean) {
        list.filter(checkX).forEach { x ->
            println(x)
        }
    }

    fun filterLetter() {
        printX { it == "x" }
    }
}

class Reason {
    fun getExtraProvider(isFriendly: Boolean): () -> List<String> {
        return if (isFriendly) {
            { listOf("x", "y", "z") }
        } else {
            { emptyList() }
        }
    }

    fun funinfun() {

        fun fun1() {}
        fun fun2() {}

        val x = false
        when (x) {
            true -> fun1()
            false -> fun2()
        }
    }
}

