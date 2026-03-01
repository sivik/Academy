package advanced

// DSL (Domain Specific Language) — tworzenie własnego "języka" wewnątrz Kotlina
// Dzięki lambda with receiver i @DslMarker możemy pisać czytelny, budujący kod
// przypominający HTML, konfiguracje, query builders itp.

// ---- 1. @DslMarker — zapobiega zagnieżdżeniu elementów tego samego poziomu ----
// Bez @DslMarker można by pisać html { html { } } co byłoby błędem logicznym
@DslMarker
annotation class HtmlDsl

// ---- 2. Klasy reprezentujące węzły HTML ----

@HtmlDsl
open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()
    private val attributes = mutableMapOf<String, String>()
    var text: String = ""

    fun attr(key: String, value: String) {
        attributes[key] = value
    }

    protected fun <T : Tag> addChild(tag: T, block: T.() -> Unit): T {
        tag.block()
        children.add(tag)
        return tag
    }

    override fun toString(): String {
        val sb = StringBuilder()
        val attrStr = if (attributes.isEmpty()) ""
        else " " + attributes.entries.joinToString(" ") { "${it.key}=\"${it.value}\"" }

        sb.append("<$name$attrStr>")
        if (text.isNotEmpty()) sb.append(text)
        children.forEach { sb.append("\n  $it") }
        sb.append("</$name>")
        return sb.toString()
    }
}

@HtmlDsl
class Html : Tag("html") {
    fun head(block: Head.() -> Unit) = addChild(Head(), block)
    fun body(block: Body.() -> Unit) = addChild(Body(), block)
}

@HtmlDsl
class Head : Tag("head") {
    fun title(block: Title.() -> Unit) = addChild(Title(), block)
}

@HtmlDsl
class Title : Tag("title")

@HtmlDsl
class Body : Tag("body") {
    fun h1(block: H1.() -> Unit) = addChild(H1(), block)
    fun p(block: P.() -> Unit) = addChild(P(), block)
    fun div(block: Div.() -> Unit) = addChild(Div(), block)
}

@HtmlDsl
class H1 : Tag("h1")

@HtmlDsl
class P : Tag("p")

@HtmlDsl
class Div : Tag("div") {
    fun p(block: P.() -> Unit) = addChild(P(), block)
}

// ---- 3. Funkcja wejściowa DSL — punkt startowy ----
fun html(block: Html.() -> Unit): Html {
    val html = Html()
    html.block()
    return html
}

// ---- 4. Przykład użycia HTML DSL ----
fun htmlDslExample() {
    val page = html {
        head {
            title { text = "Moja strona" }
        }
        body {
            h1 { text = "Witaj w DSL!" }
            p { text = "To jest paragraf." }
            div {
                attr("class", "container")
                p { text = "Paragraf wewnątrz diva." }
            }
        }
    }
    println(page)
}

// ---- 5. DSL do konfiguracji obiektu (wzorzec Builder) ----
// Zamiast wielu parametrów konstruktora używamy czytelnego bloku konfiguracji

data class ServerConfig(
    var host: String = "localhost",
    var port: Int = 8080,
    var maxConnections: Int = 100,
    var timeout: Long = 30_000L,
    var ssl: Boolean = false
)

// Lambda with receiver — blok ma dostęp do this = ServerConfig
fun serverConfig(block: ServerConfig.() -> Unit): ServerConfig {
    val config = ServerConfig()
    config.block()
    return config
}

fun serverConfigExample() {
    val config = serverConfig {
        host = "api.example.com"
        port = 443
        maxConnections = 500
        ssl = true
        timeout = 60_000L
    }
    println("Serwer: ${config.host}:${config.port}, SSL=${config.ssl}")
}

// ---- 6. DSL do budowania zapytań (Query Builder) ----

class QueryBuilder {
    private var table: String = ""
    private val conditions = mutableListOf<String>()
    private var limitValue: Int? = null
    private val selectedColumns = mutableListOf<String>()

    fun from(tableName: String) { table = tableName }
    fun select(vararg columns: String) { selectedColumns.addAll(columns) }
    fun where(condition: String) { conditions.add(condition) }
    fun limit(n: Int) { limitValue = n }

    fun build(): String {
        val cols = if (selectedColumns.isEmpty()) "*" else selectedColumns.joinToString(", ")
        val where = if (conditions.isEmpty()) "" else " WHERE " + conditions.joinToString(" AND ")
        val limit = if (limitValue != null) " LIMIT $limitValue" else ""
        return "SELECT $cols FROM $table$where$limit"
    }
}

fun query(block: QueryBuilder.() -> Unit): String {
    val builder = QueryBuilder()
    builder.block()
    return builder.build()
}

fun queryBuilderExample() {
    val sql = query {
        from("users")
        select("id", "name", "email")
        where("age > 18")
        where("active = true")
        limit(10)
    }
    println(sql)
    // SELECT id, name, email FROM users WHERE age > 18 AND active = true LIMIT 10
}

fun main() {
    println("=== HTML DSL ===")
    htmlDslExample()

    println("\n=== Server Config DSL ===")
    serverConfigExample()

    println("\n=== Query Builder DSL ===")
    queryBuilderExample()
}
