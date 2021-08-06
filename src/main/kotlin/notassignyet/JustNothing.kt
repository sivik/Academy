package notassignyet


//Typ Nothing pozwala m. in. zwrocic informacje ze dalej sie nic nie zadzieje.
fun handleSocket(isAccept: Boolean){}

data class Server(val socket: Int){
    fun accept() = true
}

fun runServer(server: Server): Nothing{
    while (true){
        handleSocket(server.accept())
    }
}

fun main(){

    runServer(Server(80))
    //Edytor sam podpowiada unreachable code
    println("Something")

    //Mozna to podczepic pod exceptiony w funkcji
    fun iWillAlwaysThrowException() : Nothing =  throw Exception("Unnecessary Exception")
    iWillAlwaysThrowException()
}