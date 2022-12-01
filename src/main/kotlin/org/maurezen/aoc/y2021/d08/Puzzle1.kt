package org.maurezen.aoc.y2021.d08

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1: ListPuzzle<Pair<List<String>, List<String>>, Int> {
    override fun run(input: List<Pair<List<String>, List<String>>>): Int {
        //only do what is needed for p1
        val mapping = mutableMapOf(Pair(2, 1), Pair(3, 7), Pair(4, 4), Pair(7, 8))

        return input.map { it.second }.flatten().map { it.length }.mapNotNull { mapping[it] }.size
    }

    override fun inputName(): String {
        return "/y2021/input08-1"
    }

    override fun input(): List<Pair<List<String>, List<String>>> {
        return readStuffFromFile(inputName()) {
            val halves = it.split(" | ")
            Pair(halves[0].split(" "), halves[1].split(" "))
        }
    }

    override fun dumpInput(input: List<Pair<List<String>, List<String>>>) {
        input.forEach { println("${it.first.joinToString(" ")} | ${it.second.joinToString(" ")}") }
    }
}

fun main() {
    Puzzle1().solve()
}