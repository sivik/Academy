package advanced



class Complex(private val real: Int, private val imaginary: Int) {

    operator fun plus(c: Complex): Complex {
        return Complex(real + c.real, imaginary + c.imaginary)
    }

    operator fun minus(c: Complex): Complex {
        return Complex(real - c.real, imaginary - c.imaginary)
    }

    override fun toString(): String {
        return this.real.toString() + " + " + this.imaginary.toString() + "i"
    }
}

class SthResult(
    var warnings: MutableList<String>,
    var errors: MutableList<String>,
    var results: HashMap<Int, ArrayList<String>>
) {

    operator fun plus(other: SthResult): SthResult {
        val temp = hashMapOf<Int, ArrayList<String>>()
        temp.putAll(other.results.filter { it.value.isNotEmpty() })
        temp.putAll(this.results.filter { it.value.isNotEmpty() })

        this.errors.addAll(other.errors)
        this.warnings.addAll(other.warnings)

        return SthResult(
            results = temp,
            errors = this.errors,
            warnings = this.warnings
        )
    }
}

fun main() {
    val c1 = Complex(1, 3)
    val c2 = Complex(2, 4)
    val c3 = c1 + c2
    val c4 = c2 - c1
    println(c3)
    println(c4)

    //-------------------
    val result1 = SthResult(
        errors = arrayListOf("e1","e2"),
        warnings = arrayListOf("w1","w2"),
        results = hashMapOf(1 to arrayListOf("r1"))
    )

    val result2 = SthResult(
        errors = arrayListOf("e3","e4"),
        warnings = arrayListOf("w3","w4"),
        results = hashMapOf(2 to arrayListOf("r2"))
    )

    val fullResult = result1 + result2
}