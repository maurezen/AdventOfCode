package org.maurezen.aoc.y2022.d02

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1: ListPuzzle<Pair<String, String>, Int> {
    override fun run(input: List<Pair<String, String>>): Int {
        return input.sumOf { score(it.first, it.second) }
    }

    fun score(opponent: String, you: String): Int {
        val myScore = score(you)
        val opponentScore = score(opponent)

        var outcome = 0
        if (opponentScore == myScore) {
            outcome = 3
        } else if (opponentScore % 3 + 1 == myScore) {
            outcome = 6
        }

        return outcome + myScore
    }

    fun score(shape: String): Int {
        return shapes[shape]!!
    }

    override fun inputName(): String {
        return "/y2022/input02-1"
    }

    override fun input(): List<Pair<String, String>> {
        return readStuffFromFile(inputName()) {
            val list = it.split(" ")
            Pair(list[0], list[1])
        }
    }

    private val shapes = mapOf(Pair("A", 1), Pair("B", 2), Pair("C", 3), Pair("X", 1), Pair("Y", 2), Pair("Z", 3))
}

fun main() {
    Puzzle1().solve()
}