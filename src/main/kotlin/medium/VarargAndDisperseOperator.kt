package medium

//vararg pozwala na to, że od określonego parametru, funkcja zezwoli na wprowadzenie ich nieskończonej liczby.

fun printInts(vararg values : Int)
{
    for (v in values)
    {
        println(v)
    }
}

fun myPrint(){
    printInts(5, 7)
    printInts(5, 7, 8)
    printInts(5, 7, 8, 3, 50, 300, 87, -5, 67, 905)
}

// Vararg nie moze wystepowac dwa razy w jednej funkcji.
fun printDoubles(vararg values : Int, /*vararg*/ seconds: Double)
{
    for (v in values)
    {
        println(v)
    }
}

fun disperseOperator(){
    val integers = intArrayOf(35, 700, -99, 846)
    val integers2 = arrayOf(35, 700, -99, 846)
    //printInts(*integers) -> printInts(integers[0], integers[1], integers[2], integers[3])
    printInts(*integers)
    //printInts(*integers2)
    //printInts(*integers) -> printInts(integers[0], integers[1], integers[2], integers[3])

}