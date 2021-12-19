package org.maurezen.aoc.y2021.d18

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1 : ListPuzzle<SnailNumber, Int> {

    override fun run(input: List<SnailNumber>): Int {
        val total = input.reduce { a, b -> a + b }

        dumpInput(listOf(total))

        return total.magnitude()
    }

    override fun inputName(): String {
        return "/input18-1"
    }

    override fun input(): List<SnailNumber> {
        return readStuffFromFile(inputName()) { (this.parse(it) as SnailNode).value }
    }

    private fun parse(s: CharSequence): Node {
        if (s[0] != '[') {
            return IntNode(Integer.parseInt(s.toString()))
        }

        val shaved = s.subSequence(1, s.length - 1)
        var depth = 0
        shaved.forEachIndexed { index, char ->
            when(char) {
                '[' -> depth++
                ']' -> depth--
                ',' -> {
                    //means we found the splitter for the outermost pair
                    if (depth == 0) {
                        return SnailNode(
                            SnailNumber(
                                parse(shaved.subSequence(0, index)),
                                parse(shaved.subSequence(index + 1, shaved.length))
                            )
                        )
                    }
                }
                else -> {}
            }
        }
        throw RuntimeException("Not supposed to be here, like, ever")
    }

    override fun dumpInput(input: List<SnailNumber>) {
        input.forEach(::println)
    }
}

abstract class Node(var left: IntNode? = null, var right: IntNode? = null) {
    var depth = 0
    var parent: SnailNode? = null

    abstract fun leftmost(): IntNode
    abstract fun rightmost(): IntNode
    abstract fun isLeaf(): Boolean
    abstract fun isLeafPair(): Boolean
    abstract fun depthSet(depth: Int)
    abstract fun leftSet(target: IntNode?)
    abstract fun rightSet(target: IntNode?)
}

class IntNode(var value: Int): Node() {
    override fun leftmost(): IntNode = this
    override fun rightmost(): IntNode = this
    override fun isLeaf(): Boolean = true
    override fun isLeafPair(): Boolean = false

    override fun depthSet(depth: Int) {
        this.depth = depth
    }

    override fun leftSet(target: IntNode?) {
        left = target
    }

    override fun rightSet(target: IntNode?) {
        right = target
    }

    override fun toString(): String {
        return "$value"
    }

    fun topLeftParent(): Node? {
        var previous: Node? = this
        var next = previous?.parent
        while (next != null && next.leftmost() == this) {
            previous = next
            next = next.parent
        }
        return previous
    }

    fun topRightParent(): Node? {
        var previous: Node? = this
        var next = previous?.parent
        while (next != null && next.rightmost() == this) {
            previous = next
            next = next.parent
        }
        return previous
    }
}

//@todo this ugly ass shit should be merged with SnailNumber somehow
class SnailNode(var value: SnailNumber): Node() {

    init {
        value.first.parent = this
        value.second.parent = this
    }

    override fun leftmost(): IntNode = value.leftmost()
    override fun rightmost(): IntNode = value.rightmost()
    override fun isLeaf(): Boolean = false
    override fun isLeafPair(): Boolean = value.first.isLeaf() && value.second.isLeaf()

    override fun depthSet(depth: Int) {
        this.depth = depth
        value.first.depthSet(depth + 1)
        value.second.depthSet(depth + 1)
    }

    override fun leftSet(target: IntNode?) {
        left = target
        value.first.leftSet(target)
    }

    override fun rightSet(target: IntNode?) {
        right = target
        value.second.rightSet(target)
    }

    override fun toString(): String {
        return "$value"
    }
}

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

    fun iterationDump(): String {
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

fun main() {
    Puzzle1().solve()
}
