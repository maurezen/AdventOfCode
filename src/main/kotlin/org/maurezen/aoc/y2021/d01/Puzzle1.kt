package org.maurezen.aoc.y2021.d01

import org.maurezen.aoc.utils.readNumbersFromFile
import org.maurezen.aoc.NumericListPuzzle

open class Puzzle1: NumericListPuzzle<Int> {
    override fun run(input: List<Int>): Int {
        return input.zipWithNext().filter { it.second > it.first }.count()
    }

    override fun inputName(): String {
        return "/input01-1"
    }

    override fun input(): List<Int> {
        return readNumbersFromFile(inputName())
    }
}

fun main() {
    Puzzle1().solve()
}