package org.maurezen.aoc.y2022.d02

open class Puzzle2: Puzzle1() {
    override fun run(input: List<Pair<String, String>>): Int {
        return input.sumOf { score(it.first, findCorrectMove(it.first, it.second)) }
    }

    //@todo this is hideous but it solves the problem so idk
    //@todo I need coffee
    private fun findCorrectMove(opponent: String, outcome: String): String {
        return if ("Z" == outcome)
            if ("A" == opponent) "Y" else if ("B" == opponent) "Z" else "X"
        else if ("X" == outcome) {
            if ("A" == opponent) "Z" else if ("B" == opponent) "X" else "Y"
        } else
            opponent
    }
}

fun main() {
    Puzzle2().solve()
}