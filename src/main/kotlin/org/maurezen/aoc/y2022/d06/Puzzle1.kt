package org.maurezen.aoc.y2022.d06

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1: ListPuzzle<String, List<Int>> {
    override fun run(input: List<String>): List<Int> {
        return input.map { lookForMarker(it, 4) }
    }

    protected fun lookForMarker(it: String, size: Int) = size + it.windowed(size, 1, false).indexOfFirst(String::allCharsAreDifferent)

    override fun inputName(): String {
        return "/y2022/input06-1"
//        return "/y2022/input06-1-test"
    }

    override fun input(): List<String> {
        return readFile(inputName())
    }

}

private fun String.allCharsAreDifferent(): Boolean {
    return chars().distinct().count().compareTo(length) == 0
}

fun main() {
    Puzzle1().solve()
}