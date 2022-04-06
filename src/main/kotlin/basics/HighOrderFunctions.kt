package basics

fun main() {
    KotlinFunctions().noExtFunctions()
    KotlinFunctions().noExtFunctions()
}

class KotlinFunctions {
    private fun someFunc() {}

    fun noExtFunctions() {

        /*===========================================*/
        //#1 run
        //Pozwala wykonać dowoną akcje dając dostęp do składowych klasy, oraz przesłaniać zmienne
        val mood = "I am sad"
        val x = run {
            val mood = "I am happy"
            println(mood) // I am happy
            return@run "something"
        }
        println(mood) // I am sad
        println(x) //Something
        /*===========================================*/
        /*===========================================*/
        //2# Zabezpieczenie przed nullem w obiekcie podczas wykoywania na nim akcji
        //Spojrzmy na przykladowy kod robiacy to samo
        val w = Window()
        with(w.settings) {
            isMinimalized = true
            isDisabled = true
        }

        w.settings.run {
            isMinimalized = true
            isDisabled = true
        }

        //Jak widać w przypadku run kod jest bardziej przejrzysty
        with(w.settings) {
            this?.isMinimalized = true
            this?.isDisabled = true
        }

        w.settings?.run {
            isMinimalized = true
            isDisabled = true
        }
        /*===========================================*/

    }


    //Higher-Order Functions

    fun extFunctions() {
        //#1 T.run
        //Możemy wywoływać akcję na danym obiekcie, w srodku mamy dostęp
        // do skłądowych obiektu/parametru na którym wykorzystujemy funckje run
        // Receiver (this)                || Argument (it)        || can return
        //this@ actualObject/parameter    || N\A                  || yes -> DiffrentType
        val stringVariable = "..."
        stringVariable.run {
            println("The length of this String is $length")
            "something"  //can return "something"
        }
        /*==========================================================================*/
        //#2 T.let
        //Ma te same własciwości co run ale posiada dostęp do argumentu jak i do samej klasy
        // Receiver (this)                || Argument (it)                          || can return
        //this@MyClass                    ||actualObject/parameter                  ||  yes -> DiffrentType

        val stringVariable2 = "..."
        stringVariable2.let {
            this.someFunc()
            println("The length of this String is ${it.length}")
            67
        }

        /*==========================================================================*/
        val stringVariable3: String? = null
        //T.let oraz T.run można również wykorzystać jako alternatywę dla testowania na wartość null
        stringVariable3?.let {
            println("The non null string is $it")
        }

        stringVariable3?.run {
            println("The non null string is $this")
        }

        //Przewaga w tej kwestii jest lepsza w przypadku let można zmienić warttość parametru, a w przypadku run jest to niezmienny this
        stringVariable3?.let { myNewNameForString ->
            println("The non null string is $myNewNameForString")
        }

        //Let oraz run swietnie nadaja się do laczenia gdy dla wartosci null chcemy  wykonac operacje np.
        stringVariable3?.let {
            println("Not null")
        }?.run {
            println("Is null1")
            println("Is null2")
            println("Is null3")
        }


        /*===================================================================================================================*/

        //#3 T.also
        //Przypomina bardzo funcje let. Wystepuje jednak subtelna róznica w tym co zwracają.
        // T.let zwraca inny typ wartości, podczas gdy T.also zwraca siebie samą
        // Receiver (this)                || Argument (it)                          || can return
        //this@Class                      ||actualObject/parameter                  ||  yes -> Yourself
        val stringVariable5: String? = null
        stringVariable5?.also {
            this.someFunc()
            println("The length of this String is ${it.length}")
            /*Zadna inna wartość nie moze zostać zwrocona
            45
            return@also 45
            */
        }

        //Zwrócenie samej siebie
        val stringVariable6: String? = "ABC"
        val d = stringVariable6?.also {
            it.plus("D")
            //it += "D" (nie można nadpisać bazowego parametru)
        }
        println(d)  //ABC

        /*===================================================================================================================*/
        //T.let jak i T.also przydatne do łączenia w łańcuchy, gdzie T.let pozwala ci ewoluować operację, a
        // T.also pozwala ci wykonywać operacje na tej samej zmiennej

        val original = "abc"
        // Evolve the value and send to the next chain
        original.let {
            println("The original String is $it") // "abc"
            it.reversed() // evolve it as parameter to send to next let
        }.let {
            println("The reverse String is $it") // "cba"
            it.length  // can be evolve to other type
        }.let {
            println("The length of the String is $it") // 3
        }
        // Wrong
        // Same value is sent in the chain (printed answer is wrong)
        original.also {
            println("The original String is $it") // "abc"
            it.reversed() // even if we evolve it, it is useless
        }.also {
            println("The reverse String is $it") // "abc"
            it.length  // even if we evolve it, it is useless
        }.also {
            println("The length of the String is $it") // "abc"
        }
        // Corrected for also (i.e. manipulate as original string
        // Same value is sent in the chain
        original.also {
            println("The original String is $it") // "abc"
        }.also {
            println("The reverse String is ${it.reversed()}") // "cba"
        }.also {
            println("The length of the String is ${it.length}") // 3
        }
        /*===================================================================================================================*/
        //4# T.apply
        //Służy do inicjowania obiektów i daje możliwość wywołania funkcji zwraca samą siebie
        // Receiver (this)                || Argument (it)      || can return
        //this@Class                      ||N\A                 ||  yes -> Yourself
        val window = Window().apply {
            this.settings.isDisabled = false
            this.SetBackground("Blue")
        }

        //Możemy również również umożliwić tworzenie łańcuchów obiektów bez łańcuchów.
        //  fun createIntent(intentData: String, intentAction: String) =
        //      Intent().apply { action = intentAction }
        //              .apply { data = Uri.parse(intentData) }
        ///====================================================================

        //#5 with
        // Jest wygodna, gdy musisz wywołać wiele różnych metod na tym samym obiekcie.
        // Zamiast powtarzać zmienną zawierającą ten obiekt w każdym wierszu,
        // Receiver (this)                || Argument (it)        || can return
        //this@ actualObject/parameter    || N\A                  || yes -> DiffrentType
        val w = Window()

        val t = with(w) {
            SetWidth(100)
            SetHeight(200)
            SetBackground("RED")
            43
        }
        println(t) // 43


    }
}


class Window {
    class Settings {
        var isDisabled: Boolean? = null
        var isMinimalized: Boolean? = null
    }

    lateinit var settings: Settings
    fun SetHeight(height: Int) {}
    fun SetWidth(width: Int) {}
    fun SetBackground(background: String) {}
}


//Dodatek
//Jak można w łatwy sposob na pomoca funkcji utworzyć łancuch
//// Normal approach
//fun makeDir(path: String): File {
//    val result = File(path)
//    result.mkdirs()
//    return result
//}
//// Improved approach
//fun makeDir(path: String) = path.let{ File(it) }.also{ it.mkdirs() }