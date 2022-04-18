package advanced

abstract class Ingredient(val name : String)
class Stone : Ingredient("Kamień")
class Diamond : Ingredient("Diament")

data class Mission<out T : Ingredient>(val reward : T)

class Player<T : Ingredient>
{
    val ingredients : MutableList<T> = mutableListOf()

    fun completeMission(mission : Mission<T>)
    {
        println("Gracz ukończył misję! Nagroda: ${mission.reward.name}")
        ingredients.add(mission.reward)
    }

    fun displayIngredients()
    {
        println("LISTA ZDOBYTYCH SKŁADNIKÓW")
        println("==========================")

        for (i in ingredients)
        {
            println(i.name)
        }

        println("==========================")
    }
}

fun main(){
    val character = Player<Ingredient>()
    val mission1 = Mission(Stone())
    val mission2 = Mission(Diamond())

    character.completeMission(mission1)
    character.displayIngredients()
    character.completeMission(mission2)
    character.displayIngredients()
}