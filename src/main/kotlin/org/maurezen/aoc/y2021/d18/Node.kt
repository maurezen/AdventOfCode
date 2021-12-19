package org.maurezen.aoc.y2021.d18

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
    abstract fun deepCopy(): Node
}