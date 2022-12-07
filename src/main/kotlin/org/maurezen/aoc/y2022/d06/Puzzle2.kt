package org.maurezen.aoc.y2022.d06

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle2: Puzzle1() {
    override fun run(input: List<String>): List<Int> {
        return input.map { lookForMarker(it, 14) }
    }

    override fun inputName(): String {
        return "/y2022/input06-1"
//        return "/y2022/input06-1-test"
    }
}

fun main() {
    Puzzle2().solve()
}