package medium

//Range
fun myRange() {
    (2..4).forEach{
        print(it)
    }
    //234
}

//Until
fun myUntil(){
    for (i in 1 until 4) {
        print(i)
    }
    // 123
}


//Break -  sluzy do natychmiastowego wyjscia z petli
//Continue - przechodzi do kolejnej iteracji z petli
fun breakAndContinue(){
    // Wyzanczenie etykiety przerwania
    customLabel@ for(y in 10 downTo 5) {
        if(y == 6) {
            print("x is $y breaking here")
            break@customLabel // wywolanie etykiety
        } else {
            print("continue to next iteration")
            continue@customLabel
        }
    }
}

fun myWhen(){
    var valueLessThan100 = when(101){
        in 1 until 101 -> true
        in listOf(1,2,3,4) -> false
        else -> false

    }
}