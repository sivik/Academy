package advanced

//"bycie" typem uniwersalnym. Oznacza to, że taki typ nie ma na sobie żadnej ścisłej informacji do jakiej grupy klas
// może należeć. Łopatologicznie można stwierdzić, że jest on "niewiadomego pochodzenia".
//Konwencja nazewnicza "trzyma się" literek T oraz E. T jest literą uniwersalną (ang. "type"),
// a E używane jest przeważnie podczas programowania kolekcji i interfejsów (ang. "element").
// Można też spotkać się z parą K i V dla mapy.

class Point(var x : Int, var y : Int)

class Generic<T>(val t : T)

fun main(){
    val genericA = Generic(6)
    val genericB = Generic(Point(3, 6))

    println(genericA.t.inc())
    println(genericB.t.x)
}
//---------------------------------

open class GenericPoint(var x : Int, var y : Int)
class ExtraGeneric<T : GenericPoint>(val t : T)

class Entity(x : Int, y : Int, val model : String) : GenericPoint(x, y)

fun main2(){
    //Blad poniewaz Int nie jest pochodna klasy GenericPoind
    //val genericA = ExtraGeneric(6)
    val genericB = ExtraGeneric(GenericPoint(3, 6))
    val genericC = ExtraGeneric(Entity(3, 6, "1234"))

    println(genericB.t.x)
    println(genericC.t.model)
}