package org.maurezen.aoc.y2021.d18

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