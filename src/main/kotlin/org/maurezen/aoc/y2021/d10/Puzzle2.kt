package org.maurezen.aoc.y2021.d10

import java.util.*

class Puzzle2: Puzzle1() {

    private val scores = mapOf(Pair(')', 1), Pair(']', 2), Pair('}', 3), Pair('>', 4))

    private val incompletes = mutableListOf<Long>()

    override fun onIncompleteString(string: String, stack: Deque<Char>) {
        var score = 0L
        while (stack.isNotEmpty()) {
            val char = stack.pop()
            score *= 5
            score += scores[brackets[char]!!]!!
        }

        incompletes.add(score)
    }

    override fun getScore(): Long {
        return incompletes.sorted()[incompletes.size/2]
    }

    override fun onCorruptedString(string: String, c: Char) {
    }
}

fun main() {
    Puzzle2().solve()
}