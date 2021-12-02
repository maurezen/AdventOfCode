package org.maurezen.aoc.y2021.d01

class Puzzle2: Puzzle1() {
    override fun run(input: List<Int>): Int {
        return input
            .windowed(3, 1, false, List<Int>::sum)
            .zipWithNext().filter { it.second > it.first }.count()
    }
}

fun main() {
    Puzzle2().solve()
}