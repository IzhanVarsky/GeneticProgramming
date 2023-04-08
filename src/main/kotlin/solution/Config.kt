package solution

import kotlin.math.pow

object Config {
    const val N = 4
    const val leftBound = -5.536
    const val rightBound = 65536.0

    //    const val rightBound = 6.0

    const val fitnessNTimes = 30

    const val crossoverProb = 0.8

    const val unaryFunctionsCount = 0
    const val binaryFunctionsCount = 200

    const val mutationNodeType = false
    const val mutationProb = 0.2

    fun targetFunction(vararg x: Double) = (0 until N).sumOf { i ->
        (x.toList().subList(0, i + 1).sum()).pow(2)
    }
}