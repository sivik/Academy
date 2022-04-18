package advanced

class Cat
class Dog

fun addDog(list: MutableList<Any>) {
    list.add(Dog())
}

fun main() {
    val cats = mutableListOf(
        Cat(),
        Cat(),
        Cat()
    )

    /*
    addDog(cats) // technically ok because Cat extends Any, but MutableList is invariant on its type parameter
    */

    cats.forEach {
        println(it)
    }

    /* if MutableList would not be invariant on its type parameter, you
     * would receive a ClassCastException at runtime
     * since Dog cannot be cast to Cat
     */
}

//Domyślnie, każdy typ generyczny jest "inwariantny".
// Oznacza to, że w miejscu parametru typu, można "wstawić" wyłącznie referencje tego samego typu.

