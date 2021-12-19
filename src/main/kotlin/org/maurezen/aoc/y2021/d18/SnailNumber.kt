package org.maurezen.aoc.y2021.d18

/**
 * Mutates provided snail numbers, if any, gluing them together with node references
 */
class SnailNumber(var first: Node, var second: Node): Iterable<IntNode> {

    init {
        first.rightSet(second.leftmost())
        second.leftSet(first.rightmost())

        listOf(first, second).forEach { it.depthSet(it.depth + 1) }
    }

    operator fun plus(another: SnailNumber): SnailNumber {
        val parent = SnailNumber(SnailNode(this), SnailNode(another))

        do {
            println(parent) //@todo
            println(parent.iterationDump())
        } while (parent.reduce())

        return parent
    }

    fun leftmost(): IntNode = first.leftmost()
    fun rightmost(): IntNode = second.rightmost()

    private fun reduce(): Boolean {
        return tryExplode() || trySplit()
    }

    private fun tryExplode(): Boolean {
        forEach {
            if (it.depth == 5 && it.parent!!.isLeafPair()) {
                val left = it
                val right = it.right!!

                val newNode = IntNode(0)

                it.parent!!.parent!!.value.replace(it.parent!!, newNode)

                if (newNode.left != null) {
                    newNode.left!!.value += left.value
                }

                if (newNode.right != null) {
                    newNode.right!!.value += right.value
                }

                return true
            }
        }
        return false
    }

    private fun trySplit(): Boolean {
        forEach {
            if (it.value >= 10) {

                val newNode = SnailNode(SnailNumber(IntNode(it.value / 2), IntNode((it.value + 1) / 2)))

                it.parent!!.value.replace(it, newNode)

                return true
            }
        }
        return false
    }

    private fun replace(what: Node, to: Node) {
        if (second == what) {
            to.rightSet(second.right)
            to.leftSet(second.left)
            to.parent = second.parent
            to.depthSet(second.depth)

            second = to
            first.rightSet(second.leftmost())
            second.right?.topLeftParent()?.leftSet(second.rightmost())
        } else {
            to.rightSet(first.right)
            to.leftSet(first.left)
            to.parent = first.parent
            to.depthSet(first.depth)

            first = to
            second.leftSet(first.rightmost())
            first.left?.topRightParent()?.rightSet(first.leftmost())
        }
    }

    fun magnitude(): Int {
        return 3*magnitude(first) + 2*magnitude(second)
    }

    private fun magnitude(n: Node): Int {
        return when(n) {
            is IntNode -> n.value
            is SnailNode -> n.value.magnitude()
            else -> -424242
        }
    }

    override fun toString(): String {
        return "[$first,$second]"
    }

    private fun iterationDump(): String {
        return "(${map(IntNode::value).joinToString(" -> ")})"
    }

    //we don't detect modifications, so you'd better not
    override fun iterator(): Iterator<IntNode> = object : Iterator<IntNode> {
        var next: IntNode? = leftmost()

        override fun hasNext(): Boolean = next != null

        override fun next(): IntNode {
            val result = next
            next = result!!.right
            return result
        }
    }
}