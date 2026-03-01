package medium

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Delegacja w Kotlinie — przekazywanie odpowiedzialności za właściwość lub klasę
// do innego obiektu. Słowo kluczowe: by

// ---- 1. by lazy — inicjalizacja przy pierwszym użyciu ----
// Wartość obliczana tylko raz, przy pierwszym dostępie. Przydatne gdy obliczenie
// jest kosztowne i może w ogóle nie być potrzebne.

class HeavyResource {
    // Blok lazy wykonuje się tylko raz, przy pierwszym odczycie właściwości
    val expensiveData: List<Int> by lazy {
        println("  [lazy] Obliczam kosztowne dane...")
        (1..1000).map { it * it }  // symulacja kosztownej operacji
    }

    val config: String by lazy {
        println("  [lazy] Wczytuję konfigurację...")
        "host=localhost;port=5432"
    }
}

fun lazyExample() {
    val resource = HeavyResource()
    println("Obiekt stworzony — dane NIE są jeszcze obliczone")
    println("Suma: ${resource.expensiveData.sum()}")   // pierwsze użycie — oblicza
    println("Suma: ${resource.expensiveData.sum()}")   // drugie użycie — z cache
    println("Config: ${resource.config}")
}

// ---- 2. by observable — reakcja na zmiany wartości ----
// Handler wywoływany za każdym razem gdy wartość się zmienia

class UserProfile {
    var name: String by Delegates.observable("(brak)") { property, oldValue, newValue ->
        println("  [observable] ${property.name}: '$oldValue' -> '$newValue'")
    }

    var age: Int by Delegates.observable(0) { _, old, new ->
        println("  [observable] wiek zmieniony: $old -> $new")
    }
}

fun observableExample() {
    val profile = UserProfile()
    profile.name = "Anna"    // wywoła handler
    profile.name = "Anna K." // wywoła handler
    profile.age = 25         // wywoła handler
}

// ---- 3. by vetoable — warunkowa zmiana wartości ----
// Jak observable, ale może zablokować zmianę (zwracając false)

class PositiveCounter {
    var count: Int by Delegates.vetoable(0) { _, _, newValue ->
        val allowed = newValue >= 0
        if (!allowed) println("  [vetoable] Odrzucono wartość ujemną: $newValue")
        allowed  // true = zmiana dozwolona, false = zmiana zablokowana
    }
}

fun vetoableExample() {
    val counter = PositiveCounter()
    counter.count = 5
    println("count = ${counter.count}")  // 5
    counter.count = -1                   // zostanie zablokowane
    println("count = ${counter.count}")  // nadal 5
    counter.count = 10
    println("count = ${counter.count}")  // 10
}

// ---- 4. by Delegates.notNull — lateinit dla typów prymitywnych ----
// Rzuca IllegalStateException przy dostępie przed przypisaniem

class GameSettings {
    var difficulty: Int by Delegates.notNull()       // Int nie może być lateinit
    var playerName: String by Delegates.notNull()
}

fun notNullExample() {
    val settings = GameSettings()
    // settings.difficulty  // IllegalStateException — jeszcze nie ustawione!
    settings.difficulty = 3
    settings.playerName = "Gracz1"
    println("Gracz: ${settings.playerName}, poziom: ${settings.difficulty}")
}

// ---- 5. Własny delegat właściwości ----
// Implementuje interfejs ReadWriteProperty<Typ klasy, Typ wartości>

class LoggingDelegate<T>(private var value: T) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("  [delegate] Odczyt '${property.name}' = $value")
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("  [delegate] Zapis '${property.name}': ${this.value} -> $value")
        this.value = value
    }
}

// Funkcja pomocnicza — czytelniejsze użycie
fun <T> logged(initialValue: T) = LoggingDelegate(initialValue)

class OrderForm {
    var customerName: String by logged("(brak)")
    var amount: Double by logged(0.0)
    var paid: Boolean by logged(false)
}

fun customDelegateExample() {
    val form = OrderForm()
    form.customerName = "Jan Kowalski"
    form.amount = 299.99
    form.paid = true
    println("Zamówienie: ${form.customerName}, ${form.amount}zł, opłacone=${form.paid}")
}

// ---- 6. Delegacja klasy (class delegation) — by z interfejsem ----
// Klasa deleguje implementację interfejsu do innego obiektu

interface Printer {
    fun print(text: String)
    fun printLine(text: String)
}

class ConsolePrinter : Printer {
    override fun print(text: String) = kotlin.io.print(text)
    override fun printLine(text: String) = println(text)
}

// LoggingPrinter deleguje całą implementację Printer do wrappedPrinter
// i nadpisuje tylko to co chce zmienić — eliminuje boilerplate
class LoggingPrinter(private val wrappedPrinter: Printer) : Printer by wrappedPrinter {
    // Nadpisujemy tylko printLine, print jest delegowane automatycznie
    override fun printLine(text: String) {
        println("[LOG] $text")
        wrappedPrinter.printLine(text)
    }
}

fun classDelegationExample() {
    val console = ConsolePrinter()
    val logging = LoggingPrinter(console)

    logging.print("Bez logu: ")    // delegowane do ConsolePrinter
    logging.printLine("Z logiem")  // nadpisane — loguje + wywołuje oryginał
}

fun main() {
    println("=== 1. by lazy ===")
    lazyExample()

    println("\n=== 2. by observable ===")
    observableExample()

    println("\n=== 3. by vetoable ===")
    vetoableExample()

    println("\n=== 4. by notNull ===")
    notNullExample()

    println("\n=== 5. Własny delegat ===")
    customDelegateExample()

    println("\n=== 6. Delegacja klasy ===")
    classDelegationExample()
}
