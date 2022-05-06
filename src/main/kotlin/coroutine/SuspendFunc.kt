package coroutine

//https://jasonxiii.pl/suspend-w-jezyku-kotlin-koprocedura-asynchroniczna
//Trzeba wiedzieć, że asynchroniczność nie równa się współbieżności!
// Współbieżność dotyczy utworzenia wielu przydziałów zadania dla każdego wątku,
// a asynchroniczność, wykonywania kilku zadań naraz W JEDNYM wątku!


//Suspend -> modyfikator funkcji informujący kompilator, że funkcja będzie mogła podlegać wstrzymaniu wykonywania jej
// instrukcji w charakterze tak zwanej "koprocedury" (ang. "coroutine"), części kodu charakterystycznej dla
// przetwarzania asynchronicznego, mającej zdolność do wstrzymywania wykonywania swoich instrukcji w dowolnym momencie.

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

var loadedBytes = 0

const val bytesCount = 80


suspend fun main(args : Array<String>)
{
    runBlocking {
        launch {
            loadFile()
        }
        showLoadingProgress()
    }
    println("Zakończone!")
}


suspend fun loadFile()
{
    while (loadedBytes < bytesCount)
    {
        if(loadedBytes < bytesCount)
        {
            loadedBytes += Random.nextInt(2, 6)
            if(loadedBytes > bytesCount)
            {
                loadedBytes = bytesCount
            }
            delay(1000)
        }
    }
}

suspend fun showLoadingProgress()
{
    while (loadedBytes < bytesCount)
    {
        delay(4000)
        val percent = ((loadedBytes.toDouble() / bytesCount.toDouble())*100).toInt()
        println("Postęp wczytywania pliku: $loadedBytes/$bytesCount bajtów ($percent%)")
    }
}
