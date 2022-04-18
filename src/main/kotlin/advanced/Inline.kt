package advanced

import kotlin.system.measureTimeMillis

// Inline oznaczenie funkcji jako "wplatanej". W praktyce spowoduje to to, że w kodzie wynikowym programu
// wywołania tejże funkcji zostaną zastąpione żywo wklejoną treścią tej funkcji!
// Pociąga to za sobą dwie konsekwencje, pozytywną i negatywną. Jeśli chodzi o negatywną, to podniesie się rozmiar pliku
// wygenerowanego kodu wynikowego przez działania kompilatora, w zależności od liczby znalezionych wywołań
// (im więcej, tym gorzej). Zaś skutkiem pozytywnym będzie potencjalne zwiększenie wydajności działania programu w
// momencie wielokrotnego wykorzystywania funkcji w krótkim czasie, na przykład w pętli. Wywołanie funkcji jest
// również pewnym "ciężarem" dla CPU, ponieważ wymaga więcej cykli (taktów zegara) niż wykonanie tej samej treści
// funkcji bez jej wywoływania (na zewnątrz).

fun performTaskk(iterations : Int, task : (iteration : Int, max : Int) -> Unit)
{
    for (i in 1..iterations)
    {
        task(i, iterations)
    }
}


inline fun performTask(iterations : Int, task : (iteration : Int, max : Int) -> Unit)
{
    for (i in 1..iterations)
    {
        task(i, iterations)
    }
}


val executionTime = measureTimeMillis {
    performTask(10000) { iterations, max ->
        val integersList = IntRange(iterations, max).toList()
        val summary = integersList.fold(0) { n, total -> total + n }

        println(summary)
    }
}
