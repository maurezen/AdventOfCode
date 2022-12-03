package org.maurezen.aoc.y2022.d03

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle2: ListPuzzle<Triple<String, String, String>, Int> {
    override fun run(input: List<Triple<String, String, String>>): Int {
        return input.sumOf { priority(findCommonItem(it.first, it.second, it.third)) }
    }

    override fun inputName(): String {
        return "/y2022/input03-1"
//        return "/y2022/input03-1-test"
    }

    private fun findCommonItem(first: CharSequence, second: CharSequence, third: CharSequence): Char {
        val common = first.filter { second.contains(it) && third.contains(it) }
        println("Common items for string $first and string $second and string $third: $common")
        return common.first()
    }

    private fun priority(char: Char): Int {
        return if (char.isLowerCase()) 1 + (char - 'a') else 27 + (char - 'A')
    }

    override fun input(): List<Triple<String, String, String>> {
        return readFile(inputName()).windowed(3, 3, false).map { Triple(it[0], it[1], it[2]) }
    }
}

fun main() {
    Puzzle2().solve()
}