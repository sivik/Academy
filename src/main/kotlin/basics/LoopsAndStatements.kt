package basics

class Statements() {
    fun statemantsIF() {

        val isOpen = false
        val isGrey = true

        //IF - ELSE Statement
        if (isOpen) {
            //some code
        } else {
            //some code
        }

        //IF-ELSE IF Statemnt
        if (!isOpen) {

        } else if (isOpen) {

        }

        //logics operator
        if (isOpen && isGrey || isOpen && !isGrey) {
        }

        if (isOpen.and(isGrey).or(isOpen.and(isGrey.not()))) {
        }

    }

    /*W języku kotlin nie ma skrocenogo if-a. Operator ? (elvis) służy do sprawdzania nulla! */

    fun statementWHEN() {
        val myEnumValue = MyEnum.FIRST
        when (myEnumValue) {
            MyEnum.FIRST -> statemantsIF()
            MyEnum.SECOND -> statemantsIF()
            MyEnum.THIRD -> {
                statemantsIF()
                statemantsIF()
            }
        }
        //------------------------------------------------
        // when -> switch na sterydach
        var res: Any? = null

        when (res) {
            1 -> res = "One"             // if obj == 1
            "Hello" -> res = "Greeting"  // if obj == "Hello"
            is Long -> "Long"            // if obj is of type Long
            !is String -> "Not string"   // if obj is not of type String
            else -> {
                // execute this block of code
            }
        }
    }

    fun shortedIF_WHEN() {

        val x = if (true) {
            7
        } else {
            10
        }
    }

    fun shortedIF_WHEN_with_return(): Int {
        return if (true) {
            7
        } else {
            10
        }
    }
}

class Loops {

    fun doWhile() {
        println("Do - While")
        var i = 0
        do {
            i++
            print(i)
        } while (i < 5)
    }

    fun whileLoops() {
        println("While")
        var i = 0
        while (i < 10) {
            i++
            print(i)
        }
    }

    fun forLoops(){
        for (x in 0..10) {
            println(x)
        }

        for (x in 0 until 10) {
            println(x)
        }

        for (x in 0 until 10 step 2) {
            println(x) // Prints 0, 2, 4, 6, 8
        }

        for (x in 10 downTo 0 step 2) {
            println(x) // Prints 10, 8, 6, 4, 2, 0
        }
    }

    fun foreachLoops() {
        val items = arrayListOf("Some", "String")
        val otherItems = arrayListOf(1, 3, 4, 5)

        //Podejście klasyczne -> obiektowe
        for (item in items) {
            println(item)
        }

        //podejscie inline
        items.forEach { print(it) }

        //podejscie funkcyjne nadpisanie nazwy paramtru iteracji
        //items.forEach(myString -> print(myString)) <- tak nie robimy nawiasy {}, a nie ()
        //items.forEach { //Tu jest lambda }
        items.forEach { myString -> print(myString) }

        //co uwazacie za czytelniejsze?
        /*1*/
        items.forEach {
            print(it)
            otherItems.forEach {
                println(it)
            }
        }

        /*2*/
        items.forEach { string_item ->
            print(string_item)
            otherItems.forEach { number_item ->
                println(number_item)
            }
        }
    }
}

