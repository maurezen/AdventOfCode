package org.maurezen.aoc.y2021.d21

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.readStuffFromFile
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

open class Puzzle1 : ListPuzzle<Int, Int> {

    private val board = (1..10).toList()
    private val turns = 3
    private val target = 1000

    private val die = DeterministicDie(100)

    override fun run(input: List<Int>): Int {
        val positions = input.map { it - 1 }.toMutableList()
        val scores = positions.map { 0 }.toMutableList()

        var current = 0
        var rolls = 0
        while (scores.none { it >= target }) {
            var move = 0
            repeat(turns) {
                move += die.next()
            }
            positions[current] = (positions[current] + move) % board.size
            scores[current] += board[positions[current]]
            current = (current + 1) % scores.size
            rolls += 3
        }

        return rolls * scores.minOf { it }
    }

    private fun steps() = 2

    override fun inputName(): String {
        return "/input21-1"
    }

    override fun input(): List<Int> {
        return readStuffFromFile(inputName()) { Integer.parseInt(it, it.lastIndex, it.lastIndex + 1, 10) }
    }

    override fun dumpInput(input: List<Int>) {
        input.forEachIndexed { index, i -> println("Player ${index+1} starting position: $i") }
    }
}

interface Die {
    fun next(): Int
}

class DeterministicDie(val size: Int): Die {

    private var next = 1

    override fun next(): Int {
        val result = next
        next++
        if (next > size) next = 1
        return result
    }

}

fun main() {
    Puzzle1().solve()
}
