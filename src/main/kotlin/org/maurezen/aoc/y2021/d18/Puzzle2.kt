package org.maurezen.aoc.y2021.d18

class Puzzle2: Puzzle1() {

    override fun run(input: List<SnailNumber>): Int {
        return (input.indices x input.indices)
            .filterNot { it.first == it.second }
            .maxOf { (input[it.first] + input[it.second]).magnitude() }
    }
}

fun main() {
    Puzzle2().solve()
}