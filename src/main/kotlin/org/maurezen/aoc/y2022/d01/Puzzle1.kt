package org.maurezen.aoc.y2022.d01

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.splitByEmptyString

open class Puzzle1: ListPuzzle<List<Int>, Int> {
    override fun run(input: List<List<Int>>): Int {
        return input.maxOf(List<Int>::sum)
    }

    override fun inputName(): String {
        return "/y2022/input01-1"
    }

    override fun input(): List<List<Int>> {
        return splitByEmptyString(readFile(inputName())) { Integer.parseInt(it.trim())}
    }
}

fun main() {
    Puzzle1().solve()
}