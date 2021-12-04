package org.maurezen.aoc.y2021.d04

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle2: Puzzle1() {
    /**
     * Assuming equal string lengths
     * Assuming non-empty input
     */
    override fun run(input: Pair<List<Int>, List<Board>>): Int {
        val winningBoards = mutableSetOf<Int>()
        input.first.forEach { number ->
            input.second.forEachIndexed { index, board ->
                if (board.mark(number)) {
                    if (!winningBoards.contains(index)) {
                        winningBoards.add(index)
                    }
                    if (winningBoards.size == input.second.size) {
                        return board.score(number)
                    }
                }
            }
        }
        return -42
    }
}

fun main() {
    Puzzle2().solve()
}