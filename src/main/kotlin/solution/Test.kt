package solution

import java.util.Random

fun main() {
    val factory = Factory()
    val p1 = factory.generateRandomCandidate(Random())
//    val p1 = Solution(listOf(VarNode(0)))
    println(p1.myToString())

    val res = Config.targetFunction(*arrayOf(1, 2, 3, 4).map { it.toDouble() }.toDoubleArray())
    println(res)
    println(res == 146.0)
}