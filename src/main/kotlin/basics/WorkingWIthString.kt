package basics

import java.util.*

fun main() {
    workWithCapitalizelLetters()
    workWithSubstring()
    workWithReplace()
    workWithContains()
    workWithLength()
    workWithToString()
    workWithStartEndsWith()
    concatAndInterpolation()
    workWithEmptyAndBlank()
    workWithFirstAndLast()
    workWithmaxMin()
    workWithNoneAndRandom()
    workWithSplit()
    workWithTrim()
    workWithPadding()
    matches()
}

fun workWithSubstring() {
    val myString = "Ameba umysłowa - nowe zwierzę dla sebka"
    println(myString.substring(3)) //ba umysłowa - nowe zwierzę dla sebka
    println(myString.substring(3, 12)) //ba umysło
    println(myString.substringAfter("-")) // nowe zwierzę dla sebka
    println(myString.substringBefore("-")) //Ameba umysłowa
}

fun workWithReplace() {
    val myString = "Nowa honda dla chomika! Juz przejechał pół chodnika"
    println(myString.replace("a", "u")) //Nowu hondu dlu chomiku! Juz przejechuł pół chodniku
    println(myString.replaceAfter("!", "i przejeżdża wtedy dzika")) //Nowa honda dla chomika!i przejeżdża wtedy dzika
    println(myString.replaceBefore("!", "Nowa Astra dla rolnika")) //Nowa Astra dla rolnika! Juz przejechał pół chodnika
    println(myString.replaceFirst("a", "x")) //Nowx honda dla chomika! Juz przejechał pół chodnika
}

fun workWithCapitalizelLetters() {
    //zmiana na duza litere pierwszego znaku
    println("abcd".capitalize()) // Abcd
    println("abcd".replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) // Abcd

    //zmiana na mala litere pierwszego znaku
    println("Abcd".decapitalize()) // abcd
    println("Abcd".replaceFirstChar { it.lowercase(Locale.getDefault()) }) // abcd

    //zmiana na duze litery caly ciag znakow
    println("asdadw".toUpperCase()) //ASDADW
    println("asdadw".uppercase(Locale.getDefault())) //ASDADW

    //zmiana na male litery caly ciag znakow
    println("ASDADW".toLowerCase()) //asdadw
    println("ASDADW".lowercase(Locale.getDefault())) //asdadw
}

fun workWithContains() {
    val myString = "Mam ubezpieczenie na spadajacy meteor"
    println(myString.contains("meteor")) //true
    println(myString.contains("meteoryt")) //false
    println(myString.contentEquals("Mam ubezpieczenie na spadajacy meteor")) //true

}

fun workWithLength() {
    println("Moja kora mózgowa posiada żywicę".length) //32
}

fun workWithToString() {
    val number = 99
    println(number.toString()) //99
}

fun workWithStartEndsWith() {
    println("Gdzie sa moje buraczki?".startsWith("Gdzie")) //true
    println("Gdzie sa moje buraczki?".startsWith("Koło zepsutej tartaczki!")) //false
    println("Gdzie sa moje buraczki?".endsWith("buraczki?")) //true
    println("Gdzie sa moje buraczki?".endsWith("wykałaczki!")) //false
}

fun concatAndInterpolation() {
    val start = "Konsumpcja zwierząt"
    val mid = " jest średnio szkodliwa dla środowiska"
    val end = " ale surowe mięsko lubię"
    //Lączenie stringow
    println(start + mid + end) //Konsumpcja zwierząt jest średnio szkodliwa dla środowiska ale surowe mięsko lubię
    println(start.plus(mid).plus(end)) //Konsumpcja zwierząt jest średnio szkodliwa dla środowiska ale surowe mięsko lubię

    //string format
    println(
        "%s jakby to powiedzieć %s chociaz to dziwne %s".format(
            start,
            mid,
            end
        )
    ) // //Konsumpcja zwierząt jakby to powiedzieć  jest średnio szkodliwa dla środowiska chociaz to dziwne  ale surowe mięsko lubię
    //interpolacja
    println("$start jakby to powiedzieć $mid chociaz to dziwne $end") //Konsumpcja zwierząt jakby to powiedzieć  jest średnio szkodliwa dla środowiska chociaz to dziwne  ale surowe mięsko lubię
}

fun workWithEmptyAndBlank() {
    val myString = "Coca cola zero"
    val myString2 = ""
    val myString3 = "     "

    println(myString2.isNullOrEmpty()) //true
    println(myString3.isNullOrBlank()) //true

    println(myString3.isBlank())//true
    println(myString2.isEmpty()) //true

    println(myString3.isNotBlank()) //false
    println(myString.isNotEmpty()) //true

}

fun workWithFirstAndLast() {
    println("last_character".last()) //r
    println("last_character".first()) //l
}

fun matches() {
    //tutaj regexy z Bartkiem
}

fun workWithmaxMin() {
    //zwaraca znak o najwyzszej wartosci
    println("workaround".maxOrNull()) //w
    println("91937".maxOrNull()) //9

    //zwaraca znak o najwyzszej wartosci
    println("workaround".minOrNull()) //a
    println("91937".minOrNull()) //1
}

fun workWithNoneAndRandom() {
    println("my_epicstring".none()) //zwroci true jesli nie ma znakow
    println("my_epicstring".random()) //losowy znak z stringa
}

fun workWithSplit() {
    println("lubie;loszki;lego".split(";")) //[lubie, loszki, lego]
}

//usuwanie tabow spacji, bialych znakow
fun workWithTrim() {
    val s = " Krowa\t"
    println("s has ${s.length} characters") //7

    val s1 = s.trimEnd()
    println("s1 has ${s1.length} characters") //6

    val s2 = s.trimStart()
    println("s2 has ${s2.length} characters") //6

    val s3 = s.trim()
    println("s3 has ${s3.length} characters") //5
}

fun workWithPadding() {
    val nums = intArrayOf(657, 122, 3245, 345, 99, 18)

    nums.toList().forEach { number_from_list -> println(number_from_list.toString().padStart(20, '.')) }
    /*
    .................657
    .................122
    ................3245
    .................345
    ..................99
    ..................18
    */

    nums.toList().forEach { number_from_list -> println(number_from_list.toString().padEnd(20, '.')) }
    /*
    657.................
    122.................
    3245................
    345.................
    99..................
    18..................
     */
}


//dodac joina
