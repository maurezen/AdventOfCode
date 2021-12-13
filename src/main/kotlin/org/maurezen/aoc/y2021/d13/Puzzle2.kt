package org.maurezen.aoc.y2021.d13

open class Puzzle2 : Puzzle1() {

    override fun shouldFold(folds: MutableList<Pair<Int, Int>>): Boolean {
        return folds.isNotEmpty()
    }
}

fun main() {
    Puzzle2().solve()
}