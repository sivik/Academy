package medium

// ============================================================
// NESTED TYPE ALIASES — beta w Kotlin 2.2
// ============================================================
//
// Wcześniej: typealias można było definiować TYLKO na poziomie top-level (plik/pakiet).
// Teraz: można je umieszczać WEWNĄTRZ klas, interfejsów i obiektów.
//
// Po co?
//   - Aliasy "prywatne" dla konkretnej klasy — nie zaśmiecają przestrzeni nazw
//   - Czytelniejsze sygnaturki złożonych typów generycznych
//   - Dokumentacja przez nazewnictwo: Map<String, List<Int>> → ScoreBoard
//   - Wewnętrzne typy klasy mają teraz własne aliasy
//
// Poprzednio: typealias UserMap = Map<Int, User>  // tylko top-level
// Teraz też:  class Repository { typealias Cache = Map<Int, User> }
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

// ---- 2. Aliasy generyczne wewnątrz klasy generycznej ----

class Graph<T> {
    // Aliasy upraszczają złożone typy generyczne lokalnie dla tej klasy
    typealias Node = T
    typealias Edge = Pair<T, T>
    typealias AdjacencyList = Map<T, Set<T>>

    private val edges = mutableListOf<Edge>()

    fun addEdge(from: Node, to: Node) {
        edges.add(Edge(from, to))
    }

    fun toAdjacencyList(): AdjacencyList {
        return edges.groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }
    }

    fun neighbors(node: Node): Set<T> =
        toAdjacencyList()[node] ?: emptySet()
}

fun graphExample() {
    println("\n=== Graf ===")
    val graph = Graph<String>()

    graph.addEdge("Warszawa", "Kraków")
    graph.addEdge("Warszawa", "Gdańsk")
    graph.addEdge("Kraków", "Wrocław")
    graph.addEdge("Gdańsk", "Poznań")

    val adj = graph.toAdjacencyList()
    adj.forEach { (city, neighbors) ->
        println("  $city → $neighbors")
    }
    println("  Sąsiedzi Warszawy: ${graph.neighbors("Warszawa")}")
}

// ---- 3. Aliasy w interfejsach — kontrakt z czytelną sygnaturą ----

interface DataPipeline<In, Out> {
    typealias Transform<A, B> = (A) -> B
    typealias Predicate<A> = (A) -> Boolean
    typealias Stage = Transform<In, Out>

    fun process(input: In): Out
    fun filter(predicate: Predicate<In>): DataPipeline<In, Out>
}

// ---- 4. Aliasy w companion object — "stałe typów" klasy ----

class HttpClient {
    companion object {
        // Aliasy żyjące przy klasie, ale dostępne przez HttpClient.Headers
        typealias Headers = Map<String, String>
        typealias QueryParams = Map<String, String>
        typealias Body = String

        fun get(url: String, headers: Headers = emptyMap(), params: QueryParams = emptyMap()): String {
            println("  GET $url")
            println("  Headers: $headers")
            println("  Params: $params")
            return """{"status": "ok"}"""
        }

        fun post(url: String, body: Body, headers: Headers = emptyMap()): String {
            println("  POST $url")
            println("  Body: $body")
            return """{"status": "created"}"""
        }
    }
}

fun httpClientExample() {
    println("\n=== HttpClient ===")

    // Używamy aliasów przez companion — czytelne i samodokumentujące się
    val headers: HttpClient.Headers = mapOf(
        "Authorization" to "Bearer token123",
        "Accept" to "application/json"
    )
    val params: HttpClient.QueryParams = mapOf("page" to "1", "size" to "10")

    HttpClient.get("https://api.example.com/users", headers, params)
    HttpClient.post(
        "https://api.example.com/users",
        body = """{"name": "Anna"}""",
        headers = mapOf("Content-Type" to "application/json")
    )
}

// ---- 5. Porównanie: top-level vs nested alias ----

// Top-level alias — widoczny wszędzie w pakiecie (czasem to za dużo)
typealias GlobalScoreMap = Map<String, Int>

class Tournament {
    // Nested alias — widoczny tylko w kontekście Tournament
    // Nie zaśmieca globalnej przestrzeni nazw
    typealias ScoreMap = Map<String, Int>
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

    println("  Ranking:")
    t.leaderboard().forEachIndexed { i, (name, score) ->
        println("  ${i + 1}. $name: $score pkt")
    }
}

fun main() {
    basicNestedAliasExample()
    graphExample()
    httpClientExample()
    tournamentExample()
}
