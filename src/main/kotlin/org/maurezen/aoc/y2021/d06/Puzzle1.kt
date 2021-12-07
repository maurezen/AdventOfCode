package org.maurezen.aoc.y2021.d06

import org.maurezen.aoc.NumericCSVPuzzle
import org.maurezen.aoc.NumericListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile
import java.math.BigInteger

open class Puzzle1: NumericCSVPuzzle<BigInteger> {

    override fun run(input: List<Int>): BigInteger {
        var shoal: Map<Fish, BigInteger> = input.groupBy { it }.mapValues { (_, list) -> BigInteger.valueOf(list.size*1L) }
            .mapKeys { (state, _) -> Fish(state = state) }

        repeat(simulationScope()) {
            shoal = simulateDay(shoal)
        }

        return shoal.values.reduce(BigInteger::plus)
    }



    private fun simulateDay(shoal: Map<Fish, BigInteger>): Map<Fish, BigInteger> {
        val next = mutableMapOf<Fish, BigInteger>()

        shoal.forEach { (fish, qty) -> fish.tick().forEach {
            next.merge(it, qty) { a, b -> a + b}
        } }

        return next
    }

    open fun simulationScope(): Int {
        return 80
    }

    override fun inputName(): String {
        return "/input06-1"
    }
}

fun main() {
    Puzzle1().solve()
}