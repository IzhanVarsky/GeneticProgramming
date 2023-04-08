package solution

import org.uncommons.watchmaker.framework.EvolutionaryOperator
import java.util.*
import kotlin.time.measureTime

class Mutation(
    private val mutateNodeType: Boolean,
    private val prob: Double
) : EvolutionaryOperator<Solution> {
    override fun apply(population: List<Solution>, random: Random): List<Solution> =
        population.map { solution ->
            Solution(solution.allNodes.mapIndexed { ind, myNode ->
                randMutate(myNode, ind, random)
            })
        }

    private fun randMutate(myNode: MyNode, index: Int, random: Random): MyNode =
        if (random.nextDouble() > prob || myNode is VarNode) myNode
        else if (random.nextBoolean() && mutateNodeType) {
            if (myNode is UnaryFuncNode) {
                BinaryFuncNode(index, random)
            } else {
                UnaryFuncNode(index, random)
            }
        } else myNode.mutate(index, random)

}