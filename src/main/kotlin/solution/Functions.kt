package solution

import java.util.Random
import kotlin.math.*
import kotlin.random.asKotlinRandom

enum class UnaryFunction(val runFunction: (Double) -> Double, val funcName: String) {
    ID({ it }, "id"),
    ABS(::abs, "abs"),
    SIN(::sin, "sin"),
    COS(::cos, "cos"),
    EXP(::exp, "exp"),
    ;

    operator fun invoke(x: Double): Double = runFunction(x)

    fun myToString(value: String): String = "$funcName($value)"

    companion object {
        fun getRandFunc(random: Random): UnaryFunction = values().random(random.asKotlinRandom())
    }
}

enum class BinaryFunction(val runFunction: (Double, Double) -> Double, val funcName: String) {
    SUM(Double::plus, "+"),

    //    SUB(Double::minus, "-"),
    MUL(Double::times, "*"),
//    SafeDIV({ a: Double, b: Double ->
//        if (b == 0.0) a / b
//        else if (a > 0) Double.MAX_VALUE
//        else Double.MIN_VALUE
//    }, "/"),

//    POW(Double::pow, "**"),
    ;

    operator fun invoke(x: Double, y: Double): Double = runFunction(x, y)

    fun myToString(v1: String, v2: String): String = "($v1 $funcName $v2)"

    companion object {
        fun getRandFunc(random: Random): BinaryFunction = values().random(random.asKotlinRandom())
    }
}