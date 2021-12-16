package org.maurezen.aoc.y2021.d16

open class Puzzle2 : Puzzle1() {

    override fun run(input: Packet): Long {
        return input.evaluate()
    }
}

fun main() {
    Puzzle2().solve()
}