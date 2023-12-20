import java.util.*

//Lab5 эцп эль гамаль

fun main() {
    var P = 0
    while (P == 0)
        P = inputP()  //P = 37!!!!
    var G = 0
    while (G == 0)
        G = inputG(P)
    var X = 0
    while (X == 0)
        X = inputX(P)
    val Y = gorner(G, X, P)
    var K = 0
    while (K == 0)
        K = RAND(P)
    println("K=$K")
    var m = 0
    val strk = "*АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ1234" //P = 37!!!!
    val mas = IntArray(1000)
    println("Ввод текста:")
    val vvod: String = readln()
    for (i in 0..vvod.length - 1) {
        for (j in 0..strk.length - 1) {
            if (vvod[i] == strk[j]) {
                mas[i] = j
            }
        }
    }
    for (i in vvod.indices) {
        val searchhash = gorner(mas[i], Y!!, P)
        m = (m xor searchhash!!).mod(P)
    }
    println("m = $m")
    val obratnK = evklid(K, P - 1)
    println("obratnK = $obratnK")
    val a = gorner(G, K, P)
    val b = (obratnK * (m - (a!! * X))).mod(P - 1)
    println("Распространение открытых данных S = (a=$a, b=$b), P=$P, G=$G, Y=$Y, исходный текст ")
    println("Проверка подписи")
    m = 0
    for (i in vvod.indices) {
        val searchhash = gorner(mas[i], Y!!, P)
        m = (m xor searchhash!!).mod(P)
    }
    println("m = $m")
    val A_ = gorner(G, m, P)
    println("A' = $A_")
    val first = gorner(Y, a, P)
    val second = gorner(a, b, P)
    val A__ = (first!! * second!!).mod(P)
    println("A'' = $A__")
    if (A_ == A__)
        println("Оценки подписи совпали, подпись верна")
    else ("Подпись фальшивка")
}

fun evklid(a: Int, n: Int): Int {
    val u: Array<Int> = arrayOf(0, 1, n)
    val v: Array<Int> = arrayOf(1, 0, a)
    val t: Array<Int> = arrayOf(0, 0, 0)
    while (u[2] != 0 && u[2] != 1 && v[2] != 0) {
        val q = u[2] / v[2]
        for (i in 0..2) {
            t[i] = u[i] - v[i] * q
            u[i] = v[i]
            v[i] = t[i]
        }
    }
    return if (u[2] != 1) {
        0
    } else {
        val uLit = u[0]
        if (uLit < 0) {
            val lit = uLit + n
            lit
        } else {
            uLit
        }
    }
} //нахожу закрытый ключ через расширенный алг евклида

fun gorner(a: Int?, x: Int, m: Int): Int? {
    var r = 1
    var k = 0
    var y: Int? = null
    if (m == 0) return null
    if (a == 0) return 0
    if (a == 1 || x == 0) return 1
    while (r <= x && k < 32) {
        r = (1 shl k)
        k++
    }
    k--
    if (k == 0 || k > 32) return null
    r = a!!
    y = if (x % 2 == 1) a
    else 1
    for (i in 1..k - 1) {
        r = (r * r).mod(m)
        if (((x shr i) and 1) == 1) {
            if (y != null) {
                y = (y.toInt() * r).mod(m)
            }
        }
    }
    return y
} //a^x mod m

fun RAND(P: Int): Int {
    val random = Random()
    val K = random.nextInt(2, P - 1)
    return if (simpleEvklid(K, P - 1) == false)
        0
    else K
}

fun simpleEvklid(K: Int, Pmin1: Int): Boolean {
    var num1 = K
    var num2 = Pmin1
    while (num1 != num2) {
        if (num1 > num2)
            num1 -= num2
        else
            num2 -= num1
    }
    return if (num1 == 1) {
        true
    } else {
        false
    }
} //АЛГОРИТМ ЕВКЛИДА: ЕСЛИ НОД = 1 , ТО TRUE

fun inputP(): Int {
    var P: Int = 0
    println("Введите простое число P:")
    try {
        P = readln().toInt()
        var flag = true
        for (i in 2..(P / 2)) {
            if (P % i == 0) {
                flag = false
                break
            }
        }
        return if (P < 1 || !flag) {
            println("Некорректный ввод. Попробуйте ещё раз")
            0
        } else P
    } catch (e: NumberFormatException) {
        println("Некорректный ввод. Попробуйте ещё раз")
        return 0
    }
}  //ВВОДИМ ПРОСТОЕ ЧИСЛО P

fun inputG(P: Int): Int {
    var G: Int = 0
    println("Введите целое число G, которое меньше P:")
    try {
        G = readln().toInt()
        return if (G < 1 || G >= P) {
            println("Некорректный ввод. Попробуйте ещё раз")
            0
        } else G
    } catch (e: NumberFormatException) {
        println("Некорректный ввод. Попробуйте ещё раз")
        return 0
    }
}  //ВВОДИМ ПРОСТОЕ ЧИСЛО P

fun inputX(P: Int): Int {
    var X: Int = 0
    println("Введите целое число X, которое меньше P:")
    try {
        X = readln().toInt()
        return if (X < 1 || X >= P) {
            println("Некорректный ввод. Попробуйте ещё раз")
            0
        } else X
    } catch (e: NumberFormatException) {
        println("Некорректный ввод. Попробуйте ещё раз")
        return 0
    }
}  //ВВОДИМ ПРОСТОЕ ЧИСЛО P