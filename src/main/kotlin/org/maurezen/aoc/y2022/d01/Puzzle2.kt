package org.maurezen.aoc.y2022.d01

open class Puzzle2: Puzzle1() {
    override fun run(input: List<List<Int>>): Int {
        return input.map(List<Int>::sum).sortedDescending().take(3).sum()
    }
}

fun main() {
    Puzzle2().solve()
}