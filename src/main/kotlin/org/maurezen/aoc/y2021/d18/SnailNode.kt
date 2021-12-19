package org.maurezen.aoc.y2021.d18

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