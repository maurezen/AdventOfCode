package org.maurezen.aoc.y2022.d03

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1: ListPuzzle<Pair<String, String>, Int> {
    override fun run(input: List<Pair<String, String>>): Int {
        return input.sumOf { priority(findCommonItem(it.first, it.second)) }
    }

    override fun inputName(): String {
        return "/y2022/input03-1"
//        return "/y2022/input03-1-test"
    }

    private fun findCommonItem(first: CharSequence, second: CharSequence): Char {
        val common = first.filter { second.contains(it) }
        println("Common items for string $first and string $second : $common")
        return common.first()
    }

    private fun priority(char: Char): Int {
        return if (char.isLowerCase()) 1 + (char - 'a') else 27 + (char - 'A')
    }

    override fun input(): List<Pair<String, String>> {
        return readStuffFromFile(inputName()) {
            Pair(
                it.substring(0, it.length / 2),
                it.substring(it.length / 2, it.length)
            )
        }
    }
}

fun main() {
    Puzzle1().solve()
}