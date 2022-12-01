package org.maurezen.aoc.y2021.d07

import org.maurezen.aoc.NumericCSVPuzzle
import kotlin.math.abs

open class Puzzle1: NumericCSVPuzzle<Int> {
    override fun run(input: List<Int>): Int {
        val min = input.minOrNull()!!
        val max = input.maxOrNull()!!

        return fuelCost(input, (min..max).minByOrNull { fuelCost(input, it) }!!)
    }

    protected open fun fuelCost(positions: List<Int>, target: Int): Int = positions.sumOf { abs(it - target) }

    override fun inputName(): String {
        return "/y2021/input07-1"
    }
}

fun main() {
    Puzzle1().solve()
}