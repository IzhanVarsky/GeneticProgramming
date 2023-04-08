package solution

import org.uncommons.maths.random.Probability
import org.uncommons.watchmaker.framework.operators.AbstractCrossover
import java.util.Random

class Crossover(prob: Double) : AbstractCrossover<Solution>(1, Probability(prob)) {
    public override fun mate(p1: Solution, p2: Solution, i: Int, random: Random): List<Solution> =
        runCrossover(p1, p2, random)

    private fun runCrossover(p1: Solution, p2: Solution, random: Random): List<Solution> {
        val bits = (1..p1.allNodes.size).map { random.nextBoolean() }
        val sol1 = bits.mapIndexed() { index: Int, b: Boolean ->
            if (b) p1.allNodes[index] else p2.allNodes[index]
        }
        val sol2 = bits.mapIndexed() { index: Int, b: Boolean ->
            if (!b) p1.allNodes[index] else p2.allNodes[index]
        }
        return listOf(Solution(sol1), Solution(sol2))
    }
}