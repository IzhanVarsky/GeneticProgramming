package solution

import org.uncommons.watchmaker.framework.EvolutionaryOperator
import org.uncommons.watchmaker.framework.SteadyStateEvolutionEngine
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline
import org.uncommons.watchmaker.framework.selection.RankSelection
import org.uncommons.watchmaker.framework.termination.GenerationCount
import java.util.*

object RunAlgo {
    private var bestValue = Double.MAX_VALUE
    private var bestIter = -1
    private var bestSolution = Solution(listOf())

    @JvmStatic
    fun main(args: Array<String>) {
        val populationSize = 50 // size of population
        val generations = 30000 // number of generations

        val random = Random() // random

        val factory = Factory() // generation of solutions

        val operators = listOf<EvolutionaryOperator<Solution>>(
            Crossover(Config.crossoverProb),
            Mutation(Config.mutationNodeType,Config.mutationProb)
        )
        val pipeline = EvolutionPipeline(operators)

        val selection = RankSelection() // Selection operator

        val evaluator = FitnessFunction(Config.fitnessNTimes) // Fitness function

        val algorithm = SteadyStateEvolutionEngine(
            factory, pipeline, evaluator, selection,
            populationSize, false, random
        )
        algorithm.addEvolutionObserver { populationData ->
            val bestFit = populationData.bestCandidateFitness
            println("Generation " + populationData.generationNumber + ": " + bestFit)
            val best = populationData.bestCandidate as Solution
            println("\tBest solution = ${best.allNodes.last().myToString(best.allNodes)}")
            if (bestFit < bestValue) {
                bestValue = bestFit
                bestIter = populationData.generationNumber
                bestSolution = best
            }
        }
        algorithm.setSingleThreaded(false)

        val terminate = GenerationCount(generations)
        algorithm.evolve(populationSize, populationSize / 6, terminate)
        println("===================")
        println("Best fit: $bestValue")
        println("Best iteration: $bestIter")
        println("Best solution: \n${bestSolution.myToString()}")
    }
}