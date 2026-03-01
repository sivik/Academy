package advanced

// ============================================================
// KOWARIANCJA — słowo kluczowe "out"
// ============================================================
//
// Przypomnij sobie Wariancja.kt: domyślnie typy generyczne są INWARIANTE,
// czyli MutableList<Stone> NIE jest podtypem MutableList<Ingredient>,
// mimo że Stone dziedziczy po Ingredient.
//
// KOWARIANCJA zmienia tę zasadę — słowo "out" przed parametrem typu T
// sprawia, że:
//
//   Mission<Stone>   JEST podtypem   Mission<Ingredient>
//
// Czyli możemy przekazać Mission<Stone> wszędzie tam, gdzie oczekujemy Mission<Ingredient>.
//
// DLACZEGO to jest bezpieczne?
//   Ponieważ "out" gwarantuje, że T jest tylko ZWRACANE (produkowane),
//   nigdy nie jest przyjmowane jako parametr metody.
//   Jeśli klasa tylko "wydaje" składniki, a Stone IS-A Ingredient,
//   to Mission<Stone> spokojnie zastępuje Mission<Ingredient>.
//
// OGRANICZENIE — "out T" oznacza:
//   ✅ T może być typem zwracanym:  fun getReward(): T
//   ❌ T NIE może być typem parametru: fun setReward(t: T)  <-- błąd kompilacji
//
// Analogia z życia:
//   Skrzynka<out Owoc> — możesz wziąć owoc ze skrzynki,
//   ale nie możesz do niej wkładać. Skrzynka jabłek to też skrzynka owoców.
//   Skrzynka<Owoc> (inwariancja) — nie możesz traktować skrzynki jabłek
//   jak skrzynki owoców, bo ktoś mógłby włożyć pomarańczę.
// ============================================================

abstract class Ingredient(val name: String)
class Stone : Ingredient("Kamień")
class Diamond : Ingredient("Diament")

// "out T" — T jest tylko produkowane (zwracane przez reward), nigdy konsumowane
data class Mission<out T : Ingredient>(val reward: T)

// ---- Kluczowa demonstracja kowariancji ----
fun showCovariance() {
    val stoneMission: Mission<Stone> = Mission(Stone())
    val diamondMission: Mission<Diamond> = Mission(Diamond())

    // Dzięki "out" możemy przypisać Mission<Stone> do Mission<Ingredient>
    // Bez "out" kompilator by na to nie pozwolił (tak jak w Wariancja.kt)
    val ingredientMission1: Mission<Ingredient> = stoneMission
    val ingredientMission2: Mission<Ingredient> = diamondMission

    println("Nagroda 1: ${ingredientMission1.reward.name}")  // Kamień
    println("Nagroda 2: ${ingredientMission2.reward.name}")  // Diament

    // Możemy też przekazać Mission<Stone> do funkcji przyjmującej Mission<Ingredient>
    announceReward(stoneMission)
    announceReward(diamondMission)
}

fun announceReward(mission: Mission<Ingredient>) {
    println("Misja ukończona! Nagroda: ${mission.reward.name}")
}

// ---- Pełny przykład z graczem ----

class Player<T : Ingredient> {
    val ingredients: MutableList<T> = mutableListOf()

    fun completeMission(mission: Mission<T>) {
        println("Gracz ukończył misję! Nagroda: ${mission.reward.name}")
        ingredients.add(mission.reward)
    }

    fun displayIngredients() {
        println("LISTA ZDOBYTYCH SKŁADNIKÓW")
        println("==========================")
        for (i in ingredients) {
            println(i.name)
        }
        println("==========================")
    }
}

fun main() {
    println("=== Demonstracja kowariancji ===")
    showCovariance()

    println("\n=== Przykład z graczem ===")
    val character = Player<Ingredient>()
    val mission1 = Mission(Stone())
    val mission2 = Mission(Diamond())

    // Mission<Stone> i Mission<Diamond> są akceptowane jako Mission<Ingredient>
    // właśnie dzięki kowariancji ("out" w definicji Mission)
    character.completeMission(mission1)
    character.displayIngredients()
    character.completeMission(mission2)
    character.displayIngredients()
}
