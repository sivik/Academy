package basics

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Collections() {

    fun fooList() {
        //lista niemodyfikowalna - Wspomnę o jeszcze jednym ważnym punkcie. "listOf" w języku Kotlin tworzy listę niemodyfikowalną.
        // Oznacza to, że po zainicjowaniu listy wprowadzonymi wartościami (albo bez nich), nie da się później w żaden sposób
        // zmodyfikować jej zawartości niż ma to miejsce w języku Java.
        val numbers: List<Int> = listOf(13, 15, 27, -6)

        // lista modyfikowalna - W niej możemy wpływać na jej zawartość,
        // to znaczy dodawać nowe elementy, usuwać już te istniejące oraz przypisywać inne w miejsca tych obecnie
        // występujących w danym indeksie
        val listA: MutableList<Int> = mutableListOf(13, 15, 27, -6)

        listA.add(67)
        listA.add(2, -90)

        // Podając za parametr argument jaki chcemy usunąć, pozbywamy się go skutecznie z listy,
        // a reszta elementów zostaje przesunięta w jedną lub w drugą stronę szeregowo.
        // Warto wiedzieć, że jeśli metoda nie znajdzie podanego przez nas elementu,
        // nie będzie żadnych konsekwencji ani "krzyczenia", że nie znaleziono elementu.
        // Nie będzie żadnego zgłaszania wyjątku!
        listA.remove(-6)

        //"set" to jest ustawianie nowej wartości we wskazanym indeksie. Identyczna operacja jakbyście przypisywali
        // nową referencję do zmiennej ze słowem kluczowym "var".
        listA.set(0, 801)
        listA[0] = 801

        //wyczyszczenie listy
        listA.clear()

    }


    fun fooArrays() {

        //Tablice
        val someStrings = Array<String>(2) { "it = $it" }
        val someStringsTwo = Array(2) { "it = $it" }
        val stringsOrNulls = arrayOfNulls<String>(10) // returns Array<String?>

        val otherStrings = arrayOf("a", "b", "c")
        val otherObjects = arrayOf("a", "b", 5, 3.0f) //return type Any

        //ArrayList
        var list = ArrayList<String>()
        list.add("zerowy element")
        var otherLIst = arrayListOf("zerowy_element", "pierwszy_element")

        var elementZero = list.get(0)
        elementZero = list[0]
        list[0] = "inny zerowy element"


    }

    fun fooMaps() {
        //niemodyfikowalna mapa
        val literals: Map<Char, Int>  = mapOf('M' to 1000, 'D' to 500, 'C' to 100, 'L' to 50, 'X' to 10, 'V' to 5, 'I' to 1)

        //mapa modyfikowalna
        val literals1 = mutableMapOf('M' to 1000, 'D' to 500, 'C' to 100, 'L' to 50)

        //HashMap
        val map: HashMap<Int, String> = hashMapOf(1 to "x", 2 to "y", -1 to "zz")
        val map2 = hashMapOf(1 to "x", 2 to "y", -1 to "zz")
        val result = map[1]
    }

    fun fooSet() {
        // Set - to struktura danych której jej głównym zadaniem jest niedopuszczenie do pojawiania się duplikatów.
        // Drugą ważną cechą jest niezachowywanie kolejności dodawanych elementów.
        // Nazywa się to wówczas "kolekcją nieuporządkowaną".
        val names = setOf("Jacek", "Maciek", "Tomasz")
        val names1: Set<String> = setOf("Jacek", "Maciek", "Tomasz", "Maciek")
        //setOf jest niemodyfikowalna struktura
        names1.forEach { println(it) } // Jacek, Maciek, Tomasz

        //modyfikowalna struktura Set
        val names2 = mutableSetOf("Jacek", "Maciek", "Tomasz")
        //modyfikowalna struktura ma te same operacje co modyfikwoalna lista

        // HashSet
        val planets =  HashSet<String>();
        planets.addAll(listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter"));
    }


    fun fooLinked(){
        //Pozwala zapamietac kolejnosc elemntow
        val planets =  LinkedList<String>();
        planets.addAll(listOf("Earth", "Venus", "Mars"))

        val planets2 =  LinkedHashSet<String>();
        planets2.addAll(listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter"));

        val alphabetMap = linkedMapOf("A" to "Apple", "B" to "Ball", "C" to "Cat")
    }

    fun additionalCollection(){
        //Dodatkowe kolekcje, z ktorymi warto sie zapoznac:
        // - Queue, Deque
        // - Stack
        // - TreeSet, TreeMap
        // - Vector
    }
}
