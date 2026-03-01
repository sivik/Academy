package basics

// ============================================================
// GUARD CONDITIONS w when — nowość w Kotlin 2.2
// ============================================================
//
// Wcześniej: każda gałąź when mogła sprawdzać tylko TYP lub wartość.
// Teraz: możesz dodać dodatkowy warunek "if" do gałęzi — to właśnie guard condition.
//
// Składnia:
//   when (obj) {
//       is Typ if dodatkowyWarunek -> ...
//       wartość if dodatkowyWarunek -> ...
//   }
//
// WAŻNE: jeśli guard zwróci false, Kotlin próbuje kolejnej gałęzi —
// nie traktuje tego jako "brak dopasowania" dla danego typu!
// ============================================================

// ---- 1. Podstawowy przykład z sealed class ----

sealed class Notification
data class Email(val from: String, val subject: String, val spam: Boolean) : Notification()
data class Sms(val from: String, val message: String) : Notification()
data class Push(val title: String, val priority: Int) : Notification()

fun handleNotification(notification: Notification): String = when (notification) {
    is Email if notification.spam        -> "🗑️  Spam od ${notification.from} — ignoruję"
    is Email if notification.subject.startsWith("[WAŻNE]") -> "🔔 Pilny email: ${notification.subject}"
    is Email                             -> "📧 Email od ${notification.from}: ${notification.subject}"
    is Sms if notification.message.length > 160 -> "📱 Długi SMS od ${notification.from}"
    is Sms                               -> "📱 SMS od ${notification.from}: ${notification.message}"
    is Push if notification.priority > 8 -> "🚨 Pilne powiadomienie: ${notification.title}"
    is Push                              -> "🔔 Powiadomienie: ${notification.title}"
}

fun basicGuardExample() {
    val notifications = listOf(
        Email("boss@firma.pl", "[WAŻNE] Spotkanie jutro", spam = false),
        Email("spam@ads.com", "Wygraj iPhone!", spam = true),
        Email("kolega@firma.pl", "Lunch?", spam = false),
        Sms("+48123456789", "Hej, co słychać?"),
        Push("Alarm pożarowy", priority = 10),
        Push("Nowa wiadomość", priority = 3)
    )

    notifications.forEach { println(handleNotification(it)) }
}

// ---- 2. Guard na wartościach (nie tylko typach) ----

fun classifyTemperature(celsius: Double): String = when {
    celsius < -20             -> "Arktyczny mróz"
    celsius < 0               -> "Mróz"
    celsius < 10              -> "Zimno"
    celsius in 10.0..20.0     -> "Chłodno"
    celsius in 20.0..25.0     -> "Komfortowo"
    celsius < 35              -> "Ciepło"
    else                      -> "Upał"
}

// Guard conditions z when(value):
fun describeNumber(n: Int): String = when (n) {
    0                        -> "zero"
    in 1..9 if n % 2 == 0   -> "mała parzysta ($n)"
    in 1..9                  -> "mała nieparzysta ($n)"
    in 10..99 if n % 10 == 0 -> "dziesiątka ($n)"
    in 10..99                -> "dwucyfrowa ($n)"
    else                     -> "duża ($n)"
}

fun guardWithValuesExample() {
    listOf(0, 2, 3, 10, 20, 47, 100).forEach {
        println("$it → ${describeNumber(it)}")
    }
}

// ---- 3. Guard na wartościach enum ----
// Guard naprawdę błyszczy gdy ten sam case enum wymaga różnej obsługi

enum class PaymentStatus { PENDING, PROCESSING, COMPLETED, FAILED, REFUNDED }

data class Order(val id: Int, val amount: Double, val status: PaymentStatus, val vip: Boolean)

fun processOrder(order: Order): String = when (order.status) {
    PaymentStatus.COMPLETED if order.vip && order.amount > 1000 -> "⭐ VIP ${order.id}: ekspresowa realizacja"
    PaymentStatus.COMPLETED if order.amount > 500               -> "🔝 Zamówienie ${order.id}: priorytetowe"
    PaymentStatus.COMPLETED                                     -> "✅ Zamówienie ${order.id}: zrealizowane"
    PaymentStatus.PENDING   if order.vip                        -> "⭐ VIP ${order.id}: przyspieszone przetwarzanie"
    PaymentStatus.PENDING                                       -> "⏳ Zamówienie ${order.id}: oczekuje na płatność"
    PaymentStatus.FAILED    if order.amount > 500               -> "❌ Zamówienie ${order.id}: wymagana weryfikacja"
    PaymentStatus.FAILED                                        -> "❌ Zamówienie ${order.id}: spróbuj ponownie"
    else                                                        -> "ℹ️  Zamówienie ${order.id}: ${order.status}"
}

fun guardWithEnumExample() {
    listOf(
        Order(1, 250.0,  PaymentStatus.COMPLETED, vip = false),
        Order(2, 750.0,  PaymentStatus.COMPLETED, vip = false),
        Order(3, 1500.0, PaymentStatus.COMPLETED, vip = true),
        Order(4, 100.0,  PaymentStatus.PENDING,   vip = false),
        Order(5, 200.0,  PaymentStatus.PENDING,   vip = true),
        Order(6, 600.0,  PaymentStatus.FAILED,    vip = false),
        Order(7, 50.0,   PaymentStatus.FAILED,    vip = false),
        Order(8, 300.0,  PaymentStatus.REFUNDED,  vip = false)
    ).forEach { println(processOrder(it)) }
}

// ---- 4. Dlaczego guard > zagnieżdżone if ----

data class User(val name: String, val age: Int, val verified: Boolean, val premium: Boolean)

// BEZ guard — czytelność spada przy wielu warunkach
fun accessLevelOld(user: User): String {
    return if (user.age < 18) {
        "Brak dostępu (niepełnoletni)"
    } else if (!user.verified) {
        "Brak dostępu (niezweryfikowany)"
    } else if (user.premium) {
        "Dostęp premium — ${user.name}"
    } else {
        "Dostęp standardowy — ${user.name}"
    }
}

// Z guard — czytelniejsze, wszystko w jednym when
fun accessLevel(user: User): String = when {
    user.age < 18        -> "Brak dostępu (niepełnoletni)"
    !user.verified       -> "Brak dostępu (niezweryfikowany)"
    user.premium         -> "Dostęp premium — ${user.name}"
    else                 -> "Dostęp standardowy — ${user.name}"
}

// Guard naprawdę błyszczy gdy łączymy sprawdzanie TYPU z warunkami
sealed class Shape
data class Circle(val radius: Double) : Shape()
data class Rectangle(val width: Double, val height: Double) : Shape()
data class Triangle(val a: Double, val b: Double, val c: Double) : Shape()

fun describeShape(shape: Shape): String = when (shape) {
    is Circle if shape.radius == 0.0      -> "Punkt (r=0)"
    is Circle if shape.radius < 1.0       -> "Mały okrąg (r=${shape.radius})"
    is Circle                             -> "Okrąg (r=${shape.radius})"
    is Rectangle if shape.width == shape.height -> "Kwadrat (${shape.width}x${shape.height})"
    is Rectangle                          -> "Prostokąt (${shape.width}x${shape.height})"
    is Triangle if shape.a == shape.b && shape.b == shape.c -> "Trójkąt równoboczny"
    is Triangle if shape.a == shape.b || shape.b == shape.c -> "Trójkąt równoramienny"
    is Triangle                           -> "Trójkąt różnoboczny"
}

fun guardVsIfExample() {
    val shapes = listOf(
        Circle(0.0), Circle(0.5), Circle(5.0),
        Rectangle(4.0, 4.0), Rectangle(3.0, 5.0),
        Triangle(3.0, 3.0, 3.0), Triangle(5.0, 5.0, 3.0), Triangle(3.0, 4.0, 5.0)
    )
    shapes.forEach { println(describeShape(it)) }
}

fun main() {
    println("=== 1. Powiadomienia ===")
    basicGuardExample()

    println("\n=== 2. Liczby ===")
    guardWithValuesExample()

    println("\n=== 3. Zamówienia ===")
    guardWithEnumExample()

    println("\n=== 4. Kształty ===")
    guardVsIfExample()
}
