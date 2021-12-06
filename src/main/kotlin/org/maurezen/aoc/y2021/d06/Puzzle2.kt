package org.maurezen.aoc.y2021.d06

class Puzzle2: Puzzle1() {

    override fun simulationScope(): Int {
        return 256
    }
}

fun main() {
    Puzzle2().solve()
}