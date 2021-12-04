package org.maurezen.aoc.y2021.d04

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1: Puzzle<Pair<List<Int>, List<Board>>, Int> {
    /**
     * Assuming equal string lengths
     * Assuming non-empty input
     */
    override fun run(input: Pair<List<Int>, List<Board>>): Int {
        input.first.forEach { number ->
            input.second.forEachIndexed { _, board ->
                if (board.mark(number)) return board.score(number)
            }
        }
        return -42
    }

    override fun inputName(): String {
        return "/input04-1"
    }

    private val boardSize = 5

    /**
     * Assumes input has a single comma-separated string with numbers
     * Assumes delimiting strings are empty
     * Assumes boards are space separated
     */
    override fun input(): Pair<List<Int>, List<Board>> {
        val strings = readFile(inputName()).toMutableList()
        val numbers = strings.removeAt(0).split(",").map(Integer::parseInt).toList()
        val boards = mutableListOf<Board>()

        strings.removeAll(String::isEmpty)

        while (strings.isNotEmpty()) {
            boards.add(Board(strings.take(boardSize).map { it.split(" ").filter(String::isNotEmpty).map(Integer::parseInt) }))
            repeat(boardSize) { strings.removeFirst() }
        }

        return Pair(numbers, boards)
    }

    override fun dumpInput(input: Pair<List<Int>, List<Board>>) {
        println(input.first)
        println(input.second)
    }
}

fun main() {
    Puzzle1().solve()
}