package org.maurezen.aoc.y2021.d07

import kotlin.math.abs

class Puzzle2: Puzzle1() {

    override fun fuelCost(positions: List<Int>, target: Int): Int {
        return positions.sumOf { sumSeries(abs(it - target)) }
    }

    private fun sumSeries(n: Int) = n * (n+1) / 2
}

fun main() {
    Puzzle2().solve()
}