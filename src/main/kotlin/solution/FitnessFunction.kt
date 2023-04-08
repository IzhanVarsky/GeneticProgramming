package solution

import org.uncommons.watchmaker.framework.FitnessEvaluator
import java.util.*
import kotlin.math.pow

class FitnessFunction(private val nTimes: Int) : FitnessEvaluator<Solution> {
    private val random = Random()

    override fun getFitness(solution: Solution, list: List<Solution>): Double =
        (0 until nTimes).map {
            val inputs = (1..Config.N).map {
                random.nextDouble(Config.leftBound, Config.rightBound)
            }.toDoubleArray()
            val trueValue = Config.targetFunction(*inputs)
            val curResult = solution.calculate(*inputs)
            (trueValue - curResult).pow(2)
        }.average()

    override fun isNatural(): Boolean {
        return false
    }

}