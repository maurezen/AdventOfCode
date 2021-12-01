package org.maurezen.aoc.y2021.d01

import org.maurezen.aoc.NumericPuzzle

class Puzzle2: NumericPuzzle<Int> {
    override fun run(input: List<Int>): Int {
        return input
            .windowed(3, 1, false, List<Int>::sum)
            .zipWithNext().filter { it.second > it.first }.count()
    }

    override fun inputName(): String {
        return "/input01-2"
    }
}

fun main() {
    Puzzle2().solve()
}