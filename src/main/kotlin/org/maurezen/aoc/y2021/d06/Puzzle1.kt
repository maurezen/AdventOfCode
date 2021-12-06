package org.maurezen.aoc.y2021.d06

import org.maurezen.aoc.NumericListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile

class Puzzle1: NumericListPuzzle<Int> {

    private val days = 80

    override fun run(input: List<Int>): Int {
        var shoal = input.map { Fish(state = it) }

        repeat(days) {
            shoal = shoal.flatMap(Fish::tick)
        }

        return shoal.size
    }

    override fun inputName(): String {
        return "/input06-1"
    }

    /**
     * We assume there's a single comma-separated line of numbers
     */
    override fun input(): List<Int> {
        return readStuffFromFile(inputName()) { it.split(",").map(Integer::parseInt) }[0]
    }

    override fun dumpInput(input: List<Int>) {
        println(input.joinToString(","))
    }
}

fun main() {
    Puzzle1().solve()
}