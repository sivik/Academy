package advanced

// ============================================================
// CONTEXT PARAMETERS — preview w Kotlin 2.2
// ============================================================
//
// Wyobraź sobie, że masz funkcje wymagające np. Logger, Database, Config.
// Klasyczne podejścia:
//   1. Parametry jawne — addUser(user, logger, db, config) → za dużo parametrów
//   2. Globalne singletony — trudne w testowaniu
//   3. Dependency Injection (DI) framework — dużo boilerplate
//
// Context parameters to wbudowany w język mechanizm "niejawnych zależności":
//   - deklarujesz jakich kontekstów potrzebuje funkcja: context(logger: Logger)
//   - wywołujesz ją będąc "wewnątrz" tych kontekstów: with(logger) { ... }
//   - kompilator sam "wstrzykuje" kontekst — nie musisz go przekazywać ręcznie
//
// Kluczowa różnica vs extension functions:
//   Extension: fun Logger.doSth()  — this = Logger, jeden kontekst, zmienia API typu
//   Context:   context(logger: Logger) fun doSth() — logger jest parametrem,
//              można mieć WIELE kontekstów, nie modyfikujesz obcego typu
// ============================================================

// ---- Infrastruktura: typy kontekstów ----

open class Logger(val prefix: String) {
    fun log(msg: String) = println("[$prefix] $msg")
    fun warn(msg: String) = println("[$prefix] ⚠️  $msg")
    fun error(msg: String) = println("[$prefix] ❌ $msg")
}

class Database {
    private val storage = mutableMapOf<Int, String>()
    fun save(id: Int, value: String) { storage[id] = value; println("  DB: zapisano $id -> $value") }
    fun find(id: Int): String? = storage[id]
    fun delete(id: Int) { storage.remove(id) }
}

data class AppConfig(val maxRetries: Int, val environment: String, val debug: Boolean)

// ---- 1. Podstawowe użycie context ----

// Funkcja deklaruje, że potrzebuje kontekstu Logger
context(logger: Logger)
fun processData(data: String): String {
    logger.log("Przetwarzam: $data")           // dostęp do logger z kontekstu
    val result = data.uppercase().reversed()
    logger.log("Wynik: $result")
    return result
}

fun basicContextExample() {
    println("=== Podstawowe context ===")
    val appLogger = Logger("APP")

    // with() wstrzykuje kontekst — wewnątrz bloku processData "widzi" logger
    with(appLogger) {
        val result = processData("hello world")
        println("Finalny wynik: $result")
    }
}

// ---- 2. Wiele kontekstów jednocześnie ----

data class User(val id: Int, val name: String, val email: String)

// Funkcja potrzebuje TRZECH kontekstów — wszystkie są dostępne jak niejawne parametry
context(logger: Logger, db: Database, config: AppConfig)
fun createUser(user: User) {
    logger.log("Tworzę użytkownika: ${user.name}")

    if (config.debug) {
        logger.log("  Debug: email=${user.email}, env=${config.environment}")
    }

    db.save(user.id, user.name)
    logger.log("Użytkownik ${user.name} (id=${user.id}) utworzony")
}

context(logger: Logger, db: Database)
fun deleteUser(userId: Int) {
    val name = db.find(userId) ?: run {
        logger.warn("Użytkownik $userId nie istnieje")
        return
    }
    db.delete(userId)
    logger.log("Usunięto użytkownika: $name")
}

fun multipleContextsExample() {
    println("\n=== Wiele kontekstów ===")

    val logger = Logger("USER-SERVICE")
    val db = Database()
    val config = AppConfig(maxRetries = 3, environment = "dev", debug = true)

    // Zagnieżdżone with() — wewnątrz mamy dostęp do wszystkich trzech kontekstów
    with(logger) {
        with(db) {
            with(config) {
                createUser(User(1, "Anna Kowalska", "anna@example.com"))
                createUser(User(2, "Jan Nowak", "jan@example.com"))
                deleteUser(1)
                deleteUser(99) // nie istnieje
            }
        }
    }
}

// ---- 3. Propagacja kontekstu ----
// Kontekst jest automatycznie przekazywany do wywoływanych funkcji!

context(logger: Logger)
fun validateInput(input: String): Boolean {
    return if (input.isBlank()) {
        logger.warn("Puste wejście!")   // logger dostępny bez przekazywania!
        false
    } else {
        logger.log("Wejście poprawne: $input")
        true
    }
}

context(logger: Logger, db: Database)
fun processOrder(orderId: Int, description: String) {
    logger.log("Przetwarzam zamówienie $orderId")

    // validateInput ma context(logger) — logger jest tu dostępny, więc działa automatycznie!
    if (!validateInput(description)) {
        logger.error("Zamówienie $orderId odrzucone — brak opisu")
        return
    }

    db.save(orderId, description)
    logger.log("Zamówienie $orderId zapisane")
}

fun contextPropagationExample() {
    println("\n=== Propagacja kontekstu ===")
    val logger = Logger("ORDER")
    val db = Database()

    with(logger) {
        with(db) {
            processOrder(101, "Klawiatura mechaniczna")
            processOrder(102, "")  // puste — zostanie odrzucone
            processOrder(103, "Monitor 4K")
        }
    }
}

// ---- 4. Testowanie — łatwość podmiany kontekstu ----
// To największa zaleta nad globalnym singletonem!

class TestLogger : Logger("TEST") {
    val logs = mutableListOf<String>()
    // W prawdziwym projekcie nadpisałbyś log() żeby zapisywać do listy zamiast println
}

fun contextInTestingExample() {
    println("\n=== Testowanie ===")

    // W testach używasz innego loggera — bez zmiany kodu funkcji!
    val testLogger = Logger("TEST")
    val testDb = Database()
    val testConfig = AppConfig(maxRetries = 1, environment = "test", debug = false)

    with(testLogger) {
        with(testDb) {
            with(testConfig) {
                createUser(User(99, "Test User", "test@test.com"))
                println("  Baza: ${testDb.find(99)}")
            }
        }
    }

    // Produkcja — inny kontekst, ten sam kod
    val prodLogger = Logger("PROD")
    val prodDb = Database()
    val prodConfig = AppConfig(maxRetries = 3, environment = "prod", debug = false)

    with(prodLogger) { with(prodDb) { with(prodConfig) {
        createUser(User(1, "Prawdziwy Użytkownik", "user@prod.com"))
    } } }
}

fun main() {
    basicContextExample()
    multipleContextsExample()
    contextPropagationExample()
    contextInTestingExample()
}
