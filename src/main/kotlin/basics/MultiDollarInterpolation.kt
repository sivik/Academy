package basics

// ============================================================
// MULTI-DOLLAR STRING INTERPOLATION — nowość w Kotlin 2.2
// ============================================================
//
// Problem: Kotlin używa $ do interpolacji zmiennych w Stringach.
// Co gdy chcemy napisać literalny $ w stringu? Np. pisząc szablon JSON,
// skrypt shell, zapytanie GraphQL, szablon Mustache — wszędzie jest $!
//
// Dotychczasowe rozwiązania były brzydkie:
//   "SELECT * FROM ${'$'}table"      // ${'$'} — nightmare
//   "\$table"                         // escape backslashem (nie działa w raw strings!)
//
// ROZWIĄZANIE: Multi-dollar interpolation
//   $$"tekst $literal $$zmienna"
//
// Zasada: liczba $ PRZED cudzysłowem = ile $ trzeba do interpolacji.
//   $"..."   → interpoluje $ (normalny string, jak zawsze)
//   $$"..."  → interpoluje $$ , pojedynczy $ jest literałem
//   $$$"..." → interpoluje $$$, $$ i $ są literałami
// ============================================================

// ---- 1. Podstawowy przykład ----

fun basicMultiDollarExample() {
    val name = "Kotlin"
    val version = "2.2"

    // Normalny string — $ interpoluje
    val normal = "Język: $name, wersja: $version"
    println(normal)

    // $$"..." — potrzebujesz $$ żeby interpolować, $ jest literałem
    val withLiteralDollar = $$"Cena: $50 za $$name $$version"
    //                              ^^^             ^^^^^^^^^^
    //                          literalny $        interpolowane!
    println(withLiteralDollar)
    // Cena: $50 za Kotlin 2.2
}

// ---- 2. Szablony JSON bez bólu głowy ----

data class Product(val id: Int, val name: String, val price: Double)

fun toJsonTemplate(product: Product): String = $$"""
{
    "id": $${ product.id },
    "name": "$${ product.name }",
    "price": $${ product.price },
    "$schema": "https://example.com/product.schema.json",
    "$ref": "#/definitions/Product"
}
""".trimIndent()
// $schema i $ref są literalne — nie kolidują z interpolacją!

fun jsonTemplateExample() {
    val product = Product(42, "Klawiatura mechaniczna", 299.99)
    println(toJsonTemplate(product))
}

// ---- 3. Szablony shell / bash ----

fun generateBashScript(appName: String, port: Int): String = $$"""
#!/bin/bash
APP_NAME="$$appName"
PORT=$$port

echo "Uruchamiam $APP_NAME na porcie $PORT..."
# $PATH, $HOME, $USER — zmienne shell — są literałami, nie kolidują!

if [ "$PORT" -lt 1024 ]; then
    echo "Uwaga: port poniżej 1024 wymaga uprawnień root"
fi

./gradlew run --args="--port=$PORT"
echo "$$appName uruchomiony!"
""".trimIndent()
//  ^^^
//  $PATH, $HOME itp. są literalne — super!
//  $$appName i $$port są interpolowane

fun bashScriptExample() {
    println(generateBashScript("AcademyApp", 8080))
}

// ---- 4. Zapytania GraphQL / Mustache / inne szablony ----

fun buildGraphQLQuery(userId: Int, fields: List<String>): String {
    val fieldList = fields.joinToString("\n        ")
    return $$"""
        query GetUser {
            user(id: $$userId) {
                $$fieldList
            }
        }
    """.trimIndent()
    // $$ interpoluje, pojedyncze $ mogłoby być literalnym fragmentem schematu GraphQL
}

fun graphQLExample() {
    val query = buildGraphQLQuery(123, listOf("id", "name", "email", "createdAt"))
    println(query)
}

// ---- 5. Porównanie starego i nowego podejścia ----

fun comparisonExample() {
    val table = "users"
    val column = "email"

    // STARE podejście — nieczytelne
    val oldWay = "UPDATE ${table} SET verified = true WHERE ${column} = ${'$'}{value}"
    println("Stare: $oldWay")

    // NOWE podejście — czytelne
    val newWay = $$"UPDATE $$table SET verified = true WHERE $$column = $${value}"
    println("Nowe: $newWay")
    // $${value} — bo za $$ musi być { lub identyfikator; tutaj chcemy literalne ${value}
}

// ---- 6. $$$"..." — trzy dolary dla ekstremalnych przypadków ----

fun tripleInterpolationExample() {
    val currency = "EUR"
    val amount = 100

    // $$$ — potrzebujesz $$$ żeby interpolować
    // $$ i $ są literałami
    val priceTag = $$$"Cena: $$amount $$$currency ($$VAT included)"
    //                   ^^                ^^^^^^^^^^^
    //               literalny $$    interpolowane: currency
    println(priceTag)
    // Cena: $$amount EUR ($$VAT included)
}

fun main() {
    println("=== 1. Podstawy ===")
    basicMultiDollarExample()

    println("\n=== 2. JSON ===")
    jsonTemplateExample()

    println("\n=== 3. Bash script ===")
    bashScriptExample()

    println("\n=== 4. GraphQL ===")
    graphQLExample()

    println("\n=== 5. Porównanie ===")
    comparisonExample()

    println("\n=== 6. Potrójny dolar ===")
    tripleInterpolationExample()
}
