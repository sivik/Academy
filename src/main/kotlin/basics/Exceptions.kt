package basics

fun CatchMeIfYouCan(){
    try {
        // some code where app can crashed
    }
    catch (e: Exception) {
        // handle error code
    }
    finally {
        // optional finally block
    }
}

fun giveMeException(){
    throw Exception("My Exception")
}