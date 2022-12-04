package org.maurezen.aoc.y2022.d04

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1: ListPuzzle<Pair<IntRange, IntRange>, Int> {
    override fun run(input: List<Pair<IntRange, IntRange>>): Int {
        return input.filter { filter(it.first, it.second) }.count()
    }

    override fun inputName(): String {
        return "/y2022/input04-1"
//        return "/y2022/input04-1-test"
    }

    override fun input(): List<Pair<IntRange, IntRange>> {
        return readStuffFromFile(inputName()) {
            splitByComma(it)
        }
    }

    protected open fun filter(first: IntRange, second: IntRange): Boolean {
        return first.all(second::contains) || second.all(first::contains)
    }

    private fun splitByComma(string: String): Pair<IntRange, IntRange> {
        val split = string.split(",")
        return Pair(splitByDash(split[0]), splitByDash(split[1]))
    }

    private fun splitByDash(string: String): IntRange {
        val split = string.split("-")
        return Integer.parseInt(split[0])..Integer.parseInt(split[1])
    }
}

fun main() {
    Puzzle1().solve()
}