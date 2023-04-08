package solution

import java.util.*
import kotlin.random.asKotlinRandom

abstract class MyNode {
    abstract fun calculate(allNodes: List<MyNode>, vararg variables: Double): Double
    abstract fun myToString(allNodes: List<MyNode>): String
    abstract fun mutate(maxNodeIndex: Int, random: Random): MyNode
}

class VarNode(private val varInd: Int) : MyNode() {
    override fun calculate(allNodes: List<MyNode>, vararg variables: Double): Double = variables[varInd]

    override fun myToString(allNodes: List<MyNode>): String = "x$varInd"

    override fun mutate(maxNodeIndex: Int, random: Random): MyNode =
        VarNode(varInd) // No mutation
}

class UnaryFuncNode(val func: UnaryFunction, private val ancestorNodeIndex: Int) : MyNode() {
    constructor(maxNodeIndex: Int, random: Random) : this(
        UnaryFunction.getRandFunc(random),
        (0 until maxNodeIndex).random(random.asKotlinRandom())
    )

    override fun calculate(allNodes: List<MyNode>, vararg variables: Double): Double =
        func(allNodes[ancestorNodeIndex].calculate(allNodes, *variables))

    override fun myToString(allNodes: List<MyNode>): String =
        func.myToString(allNodes[ancestorNodeIndex].myToString(allNodes))

    override fun mutate(maxNodeIndex: Int, random: Random): MyNode {
        val funcType = if (random.nextDouble() > 0.5) func else UnaryFunction.getRandFunc(random)
        val ancNode = if (random.nextDouble() > 0.5) {
            ancestorNodeIndex
        } else {
            (0 until maxNodeIndex).random(random.asKotlinRandom())
        }
        return UnaryFuncNode(funcType, ancNode)
    }
}

class BinaryFuncNode(
    val func: BinaryFunction,
    private val ancestorNode1Index: Int,
    private val ancestorNode2Index: Int
) : MyNode() {
    constructor(maxNodeIndex: Int, random: Random) : this(
        BinaryFunction.getRandFunc(random),
        (0 until maxNodeIndex).random(random.asKotlinRandom()),
        (0 until maxNodeIndex).random(random.asKotlinRandom()),
    )

    override fun calculate(allNodes: List<MyNode>, vararg variables: Double): Double {
        val anc1Res = allNodes[ancestorNode1Index].calculate(allNodes, *variables)
        val anc2Res = allNodes[ancestorNode2Index].calculate(allNodes, *variables)
        return func(anc1Res, anc2Res)
    }

    override fun myToString(allNodes: List<MyNode>): String {
        val anc1Res = allNodes[ancestorNode1Index].myToString(allNodes)
        val anc2Res = allNodes[ancestorNode2Index].myToString(allNodes)
        return func.myToString(anc1Res, anc2Res)
    }

    override fun mutate(maxNodeIndex: Int, random: Random): MyNode {
        val funcType = if (random.nextDouble() > 0.5) func else BinaryFunction.getRandFunc(random)
        val ancNode1 = if (random.nextDouble() > 0.5) {
            ancestorNode1Index
        } else {
            (0 until maxNodeIndex).random(random.asKotlinRandom())
        }
        val ancNode2 = if (random.nextDouble() > 0.5) {
            ancestorNode2Index
        } else {
            (0 until maxNodeIndex).random(random.asKotlinRandom())
        }
        return BinaryFuncNode(funcType, ancNode1, ancNode2)
    }
}

class Solution(val allNodes: List<MyNode>) {
    fun calculate(vararg input: Double): Double = allNodes.last().calculate(allNodes, *input)

    fun myToString(): String = allNodes.last().myToString(allNodes)
}