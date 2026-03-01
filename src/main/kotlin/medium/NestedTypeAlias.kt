package medium

// ============================================================
// NESTED TYPE ALIASES — beta w Kotlin 2.2 (wymaga -Xnested-type-aliases)
// ============================================================
//
// Wcześniej: typealias tylko na poziomie top-level (plik/pakiet).
// Teraz: typealias wewnątrz klas, obiektów i interfejsów.
//
// Co działa w becie:
//   ✅ Aliasy dla typów konkretnych wewnątrz klasy
//   ✅ Aliasy z własnymi parametrami typów: typealias Mapper<A, B> = (A) -> B
//   ✅ Aliasy jako typy zmiennych lokalnych WEWNĄTRZ klasy/companion
//
// Ograniczenia bety:
//   ❌ Alias NIE może odwoływać się do parametru typu klasy zewnętrznej
//      Np. w class Graph<T> nie można pisać: typealias Node = T
//   ❌ Aliasy z companion/klasy nie są jeszcze dostępne spoza klasy
//      (KlasaName.AliasName nie działa) — używaj type inference lub pełnego typu
// ============================================================

// ---- 1. Podstawowy przykład — aliasy wewnątrz klasy ----

class EventSystem {
    // Aliasy opisują intencje — Map<String, List<()->Unit>> jest nieczytelne
    typealias EventName = String
    typealias Handler = () -> Unit
    typealias HandlerList = MutableList<Handler>
    typealias Registry = MutableMap<EventName, HandlerList>

    private val registry: Registry = mutableMapOf()

    fun on(event: EventName, handler: Handler) {
        registry.getOrPut(event) { mutableListOf() }.add(handler)
    }

    fun emit(event: EventName) {
        registry[event]?.forEach { it() }
            ?: println("  Brak handlerów dla: $event")
    }
}

fun basicNestedAliasExample() {
    println("=== EventSystem ===")
    val events = EventSystem()

    events.on("login") { println("  Handler1: użytkownik zalogowany") }
    events.on("login") { println("  Handler2: zapisuję do logów") }
    events.on("logout") { println("  Handler: użytkownik wylogowany") }

    events.emit("login")
    events.emit("logout")
    events.emit("click")
}

// ---- 2. Aliasy z konkretnymi typami zamiast generycznych ----
// Ograniczenie bety: alias nie może używać T z klasy zewnętrznej.
// Rozwiązanie: użyj konkretnego typu lub zrób osobną klasę.

class CityGraph {
    // Wszystkie aliasy bazują na konkretnym typie String
    typealias City = String
    typealias Route = Pair<City, City>
    typealias ConnectionMap = Map<City, Set<City>>

    private val routes = mutableListOf<Route>()

    fun addRoute(from: City, to: City) {
        routes.add(Route(from, to))
    }

    fun connections(): ConnectionMap =
        routes.groupBy({ it.first }, { it.second })
            .mapValues { (_, cities) -> cities.toSet() }

    fun neighbors(city: City): Set<City> =
        connections()[city] ?: emptySet()
}

fun graphExample() {
    println("\n=== CityGraph ===")
    val graph = CityGraph()

    graph.addRoute("Warszawa", "Kraków")
    graph.addRoute("Warszawa", "Gdańsk")
    graph.addRoute("Kraków", "Wrocław")
    graph.addRoute("Gdańsk", "Poznań")

    graph.connections().forEach { (city, neighbors) ->
        println("  $city → $neighbors")
    }
    println("  Sąsiedzi Warszawy: ${graph.neighbors("Warszawa")}")
}

// ---- 3. Aliasy z własnymi parametrami typów ----
// To działa — alias deklaruje własne A, B niezależne od klasy zewnętrznej

class Transformers {
    typealias Mapper<A, B> = (A) -> B
    typealias Filter<A>    = (A) -> Boolean
    typealias Reducer<A, B> = (B, A) -> B

    fun <A, B> transform(items: List<A>, mapper: Mapper<A, B>): List<B> =
        items.map(mapper)

    fun <A> select(items: List<A>, predicate: Filter<A>): List<A> =
        items.filter(predicate)

    fun <A, B> aggregate(items: List<A>, initial: B, reducer: Reducer<A, B>): B =
        items.fold(initial, reducer)
}

fun transformersExample() {
    println("\n=== Transformers ===")
    val t = Transformers()

    val numbers = listOf(1, 2, 3, 4, 5, 6)

    val doubled = t.transform(numbers) { it * 2 }
    println("  Podwojone: $doubled")

    val evens = t.select(numbers) { it % 2 == 0 }
    println("  Parzyste: $evens")

    val sum = t.aggregate(numbers, 0) { acc, n -> acc + n }
    println("  Suma: $sum")
}

// ---- 4. Aliasy w companion object — dostępne przez NazwaKlasy.Alias ----

class HttpClient {
    companion object {
        typealias Headers     = Map<String, String>
        typealias QueryParams = Map<String, String>
        typealias Body        = String

        // W becie aliasy są niedostępne w sygnaturach funkcji WEWNĄTRZ tego samego
        // companion — użyj ich jako typów lokalnych zmiennych lub spoza klasy
        fun get(url: String, headers: Map<String, String> = emptyMap(),
                params: Map<String, String> = emptyMap()): String {
            val h: Headers      = headers   // alias jako typ lokalny — działa!
            val p: QueryParams  = params
            println("  GET $url | headers: $h | params: $p")
            return """{"status":"ok"}"""
        }

        fun post(url: String, body: Map<String, String> = emptyMap(),
                 headers: Map<String, String> = emptyMap()): String {
            val b: Body = body.entries.joinToString(", ") { "${it.key}=${it.value}" }
            println("  POST $url | body: $b")
            return """{"status":"created"}"""
        }
    }
}

fun httpClientExample() {
    println("\n=== HttpClient ===")

    // Aliasy są dostępne wewnątrz klasy. Na zewnątrz używamy type inference lub pełnego typu.
    val headers = mapOf("Authorization" to "Bearer token123")   // = Map<String, String>
    val params  = mapOf("page" to "1", "size" to "10")          // = Map<String, String>

    HttpClient.get("https://api.example.com/users", headers, params)
    HttpClient.post("https://api.example.com/users", mapOf("name" to "Anna"))
}

// ---- 5. Porównanie: top-level vs nested — czystość przestrzeni nazw ----

// Top-level alias — widoczny wszędzie w pakiecie (czasem to za dużo)
typealias GlobalScoreMap = Map<String, Int>

class Tournament {
    // Nested alias — widoczny tylko jako Tournament.ScoreMap z zewnątrz
    // Nie zaśmieca globalnej przestrzeni nazw
    typealias ScoreMap   = Map<String, Int>
    typealias Leaderboard = List<Pair<String, Int>>

    private val scores = mutableMapOf<String, Int>()

    fun addScore(player: String, points: Int) {
        scores[player] = (scores[player] ?: 0) + points
    }

    fun leaderboard(): Leaderboard =
        scores.entries
            .sortedByDescending { it.value }
            .map { it.key to it.value }

    fun getScores(): ScoreMap = scores
}

fun tournamentExample() {
    println("\n=== Tournament ===")
    val t = Tournament()
    t.addScore("Anna", 150)
    t.addScore("Bartek", 200)
    t.addScore("Celina", 175)
    t.addScore("Anna", 50)

    // Typ zwracany jest Tournament.Leaderboard — alias "należy" do klasy
    val ranking: Tournament.Leaderboard = t.leaderboard()
    ranking.forEachIndexed { i, (name, score) ->
        println("  ${i + 1}. $name: $score pkt")
    }
}

fun main() {
    basicNestedAliasExample()
    graphExample()
    transformersExample()
    httpClientExample()
    tournamentExample()
}
