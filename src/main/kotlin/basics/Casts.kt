package basics


fun cast() {
    val x = "EXAMPLE"

    val y = x is String     //true
    val z = x !is String    //false

    val w = x as Long   //w przypadku niepowodzenia rzutowania poleci exception
    val b = x as? Long // w przypadku niepowodzenia poleci null
    //WARTO PO RZUTOWANIU SPRAWDZAC IFEM LUB WHENEM
}

fun example() {
    var list = arrayListOf<Any?>("1", "2", 3, 5, 6, true, false, null)
    list.forEach {
        when (it) {
            is String -> println(it)
        }
    }

    println(list.filter { it is String })

    println(list.filterIsInstance<String>())

    println(list.filterNotNull())
}

fun main() {
    example()
}


fun castCollections(){
    val array = arrayOf(1,2,3,4)
    val castArrayToList = array.toList()
    var castedArrayFromList = castArrayToList.toIntArray()
}