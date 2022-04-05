package basics

class Collections() {
    fun fooCollections() {

        //Tablice
        val someStrings = Array<String>(2) { "it = $it" }
        val someStringsTwo = Array(2) { "it = $it" }
        val stringsOrNulls = arrayOfNulls<String>(10) // returns Array<String?>

        val otherStrings = arrayOf("a", "b", "c")
        val otherObjects = arrayOf("a", "b", 5, 3.0f) //return type Any

        //listy
        var list = ArrayList<String>()
        list.add("zerowy element")
        var otherLIst = arrayListOf("zerowy_element","pierwszy_element")

        var elementZero = list.get(0)
        elementZero = list[0]
        list[0] = "inny zerowy element"

        //HashMap
        val map: HashMap<Int, String> = hashMapOf(1 to "x", 2 to "y", -1 to "zz")
        val map2 = hashMapOf(1 to "x", 2 to "y", -1 to "zz")
        val result = map[1]
    }


    /* Dodatkowe kolekcje które poznamy*/
    //LinkedHashMap i List
    //Mutable List i map

}
