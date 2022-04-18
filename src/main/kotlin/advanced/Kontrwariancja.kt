package advanced

import kotlin.math.sqrt


//Inwariancja -  Oznacza to, że w miejscu parametru typu, można "wstawić" włącznie referencje tego samego tpu
//Kontrwariancja - tworzana za pomoca slowa kluczowego "in" sprawia, ze mamy możliwość "wstawienia" typu klasy pochodnej
// w miejsce typu klasy bazowej

open class Character(var weight: Double, var height: Double)
open class Human(weight: Double, height: Double, val energy: String) : Character(weight, height)
open class Elf(weight: Double, height: Double, val mana: String) : Character(weight, height)
class Warrior(weight: Double, height: Double, energy: String, attack: Int) : Human(weight, height, energy)
class Knight(weight: Double, height: Double, energy: String, attack: Int) : Human(weight, height, energy)
class Mage(weight: Double, height: Double, mana: String, magicPower: Int) : Elf(weight, height, mana)
class Archer(weight: Double, height: Double, mana: String, attack: Int) : Elf(weight, height, mana)

//uzycie slowka "in" dopuszcza wstawienie klasy pochodnej w miejsce klasy bazowej.
class Distance<in T : Character, in U : Character>
{
    fun calculate(t : T, u : U) : Double
    {
        val x = u.weight - t.height
        val y = u.weight - t.height
        val sum = x*x + y*y

        return sqrt(sum)
    }

    //Kontrawariancję przy użyciu "in" w języku Kotlin można zastosować wtedy i tylko wtedy, gdy:

    // - klasa ma funkcję, która używa parametru typu T jako typu swojego parametru
    // - klasa NIE zawiera żadnych funkcji zwracających parametr typu T
    // - klasa NIE zawiera żadnych właściwości typu "parametr typu T", bez względu na to, czy użyto "var", czy "val"

    //fun sth(): T {}
}

val human = Human(4.2, 5.6, "full")
val elf = Elf(4.2, 5.6, "full")


val warrior = Warrior(80.3, 161.4, "medium", 60)
val knight = Knight(71.3, 169.4, "big", 23)
val archer = Archer(21.3, 165.4, "low", 12)
val mage = Mage(11.3, 165.4, "low", 12)

fun makeSth(){
    val dc : Distance<Human, Human> = Distance()
    val dc1 : Distance<Elf, Elf> = Distance()
    dc.calculate(warrior, knight)
    dc.calculate(human, human)
    /*
    dc.calculate(archer, mage)
    dc.calculate(elf, elf)

    dc1.calculate(warrior, knight)
    dc1.calculate(human, human)
     */
    dc1.calculate(archer, mage)
    dc1.calculate(elf, elf)

    val dc2 : Distance<Elf, Mage> = Distance()
    //dc2.calculate(mage, elf)

}