# Kotlin Academy

Repozytorium z materiałami dydaktycznymi do nauki języka Kotlin.
Każdy plik to osobny, uruchamialny temat z komentarzami w języku polskim.

## Wymagania

- JDK 17+
- Kotlin 2.2.0
- Gradle 8.11.1

## Struktura projektu

```
src/main/kotlin/
├── basics/       # Podstawy języka
├── medium/       # Programowanie obiektowe i wzorce
├── advanced/     # Zaawansowane funkcje języka
└── coroutine/    # Programowanie asynchroniczne
```

---

## 🟢 Podstawy (`basics`)

| Plik | Temat | Kluczowe pojęcia |
|------|-------|-----------------|
| `Variables.kt` | Zmienne | `val`, `var`, `const val`, nullable, `lateinit`, `by lazy` |
| `Functions.kt` | Funkcje | parametry, typy zwracane, składnia skrócona, `Unit` |
| `Collections.kt` | Kolekcje | `List`, `MutableList`, `Map`, `Set`, `Array`, `LinkedList` |
| `LoopsAndStatements.kt` | Pętle i instrukcje | `if`, `when`, `for`, `while`, `forEach`, zakresy |
| `Casts.kt` | Rzutowanie typów | `is`, `as`, `as?`, `filterIsInstance`, smart cast |
| `Exceptions.kt` | Wyjątki | `try-catch-finally`, `throw` |
| `MyEnum.kt` | Enum i Sealed class | `enum class`, `sealed class`, pattern matching |
| `ScopeFunctions.kt` | Funkcje zakresu | `let`, `run`, `apply`, `also`, `with` |
| `WorkingWIthString.kt` | Operacje na String | `substring`, `replace`, interpolacja, `trim`, `split` |
| `WhenGuards.kt` | Guard conditions ⭐ _Kotlin 2.2_ | `is Type if warunek ->`, guard na enum i sealed class |
| `MultiDollarInterpolation.kt` | Multi-dollar interpolacja ⭐ _Kotlin 2.2_ | `$$"..."`, literalny `$` w szablonach JSON/bash/GraphQL |

---

## 🟡 Średniozaawansowane (`medium`)

| Plik | Temat | Kluczowe pojęcia |
|------|-------|-----------------|
| `IntrodctionClass.kt` | Klasy i dziedziczenie | `open`, `abstract`, `interface`, modyfikatory dostępu |
| `Constructors.kt` | Konstruktory | primary, secondary, `init`, `super()`, `this()` |
| `DataClass.kt` | Data class | `equals`, `hashCode`, `toString`, `copy()` |
| `SingletonAndStatic.kt` | Singleton i static | `object`, `companion object` |
| `LIstenerCreator.kt` | Interfejsy i obiekty anonimowe | `object : Interface {}` |
| `TypeAlias.kt` | Type alias | `typealias`, typy funkcji, `invoke()` |
| `NestedTypeAlias.kt` | Zagnieżdżone type aliasy ⭐ _Kotlin 2.2 beta_ | `typealias` wewnątrz klasy, companion object |
| `VarargAndDisperseOperator.kt` | Vararg i spread | `vararg`, operator `*` |
| `KotlinAdditinalControlFlow.kt` | Dodatkowy flow | `..`, `until`, `downTo`, labeled `break`/`continue` |
| `JustNothing.kt` | Typ Nothing | funkcje niezwracające wartości |
| `Destructuring.kt` | Destrukturyzacja | `val (a, b) = obj`, `_`, `componentN()`, w lambdach |
| `Delegation.kt` | Delegacja | `by lazy`, `by observable`, `by vetoable`, własny delegat, delegacja klasy |
| `Sequences.kt` | Sekwencje | `asSequence()`, `generateSequence`, `sequence { yield }`, lazy vs eager |
| `WorkingWithCollections.kt` | Transformacje kolekcji | `map`, `flatMap`, `filter`, `fold`, `sorted`, `first`/`last` |

---

## 🔴 Zaawansowane (`advanced`)

| Plik | Temat | Kluczowe pojęcia |
|------|-------|-----------------|
| `ExtensionFunction.kt` | Funkcje rozszerzające | `fun Type.func()`, generyczne, operator extensions |
| `Infix.kt` | Infix | `infix fun`, wywołanie bez kropki i nawiasów |
| `Inline.kt` | Inline | `inline fun`, optymalizacja wydajności, `measureTimeMillis` |
| `OverridingOperators.kt` | Przeładowanie operatorów | `operator fun plus()`, `minus()` |
| `GenericTypes.kt` | Typy generyczne | `class Generic<T>`, ograniczenia górne `T : Typ` |
| `Wariancja.kt` | Inwariancja | domyślna inwariancja, bezpieczeństwo typów |
| `Kowariancja.kt` | Kowariancja | `out T`, producent, `Mission<Stone>` jako `Mission<Ingredient>` |
| `Kontrwariancja.kt` | Kontrwariancja | `in T`, konsument, hierarchia typów |
| `Reified.kt` | Reified | `reified T`, dostęp do typu w runtime, `T::class` |
| `FoldAndReduce.kt` | Fold i Reduce | `fold {}`, `reduce {}`, akumulacja, różne typy wynikowe |
| `Dsl.kt` | DSL | `@DslMarker`, lambda with receiver, HTML builder, Query builder |
| `ContextParameters.kt` | Context parameters ⭐ _Kotlin 2.2 preview_ | `context(logger: Logger)`, `with()`, niejawne zależności |

---

## 🔵 Korutyny (`coroutine`)

| Plik | Temat | Kluczowe pojęcia |
|------|-------|-----------------|
| `SuspendFunc.kt` | Podstawy | `suspend`, `runBlocking`, `launch`, `delay` |
| `AsyncAwait.kt` | Async/Await | `async {}`, `Deferred`, `await()`, `awaitAll`, `supervisorScope` |
| `CoroutineDispatchers.kt` | Dispatchery i Job | `Dispatchers.IO/Default`, `withContext`, `Job`, `SupervisorJob`, `CoroutineScope` |
| `Flow.kt` | Flow | `flow {}`, `collect`, `map`/`filter`, `StateFlow`, `SharedFlow`, obsługa błędów |

---

## Nowości Kotlin 2.x

Pliki oznaczone ⭐ demonstrują funkcje wprowadzone w Kotlin 2.0+:

| Funkcja | Wersja | Plik | Status |
|---------|--------|------|--------|
| Guard conditions w `when` | 2.2 | `WhenGuards.kt` | ✅ Stable |
| Multi-dollar string interpolation | 2.2 | `MultiDollarInterpolation.kt` | ✅ Stable |
| Nested type aliases | 2.2 | `NestedTypeAlias.kt` | 🟡 Beta |
| Context parameters | 2.2 | `ContextParameters.kt` | 🔴 Preview |

---