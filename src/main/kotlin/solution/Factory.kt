package solution

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory
import java.util.*
import kotlin.random.asKotlinRandom

class Factory : AbstractCandidateFactory<Solution>() {
    override fun generateRandomCandidate(random: Random): Solution {
        val allNodes = mutableListOf<MyNode>()
        allNodes += (0 until Config.N).map { VarNode(it) }
//        orderedFactory(allNodes, random)
        shuffledFactory(allNodes, random)
        return Solution(allNodes)
    }

    private fun orderedFactory(allNodes: MutableList<MyNode>, random: Random) {
        repeat(Config.unaryFunctionsCount) {
            allNodes += UnaryFuncNode(allNodes.size, random)
        }
        repeat(Config.binaryFunctionsCount) {
            allNodes += BinaryFuncNode(allNodes.size, random)
        }
    }

    private fun shuffledFactory(allNodes: MutableList<MyNode>, random: Random) {
        val unaryUsed = 0
        val binaryUsed = 0
        repeat(Config.unaryFunctionsCount + Config.binaryFunctionsCount) {
            val rand = random.nextBoolean()
            if (unaryUsed >= Config.unaryFunctionsCount ||
                rand && binaryUsed < Config.binaryFunctionsCount
            ) {
                allNodes += BinaryFuncNode(allNodes.size, random)
            } else {
                allNodes += UnaryFuncNode(allNodes.size, random)
            }
        }
    }
}