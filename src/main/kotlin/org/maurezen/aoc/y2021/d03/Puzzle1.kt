package org.maurezen.aoc.y2021.d03

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1: ListPuzzle<String, Int> {
    /**
     * Assuming equal string lengths
     * Assuming non-empty input
     */
    override fun run(input: List<String>): Int {
        var epsilon = ""
        var gamma = ""

        val freqMap = mutableMapOf<Int, MutableMap<Char, Int>>()

        input.first().forEachIndexed() { index, _ ->
            val indexMap = freqMap.getOrPut(index) {mutableMapOf()}

            input.forEachIndexed() { _, c ->
                indexMap.merge(c[index], 1) { t, u -> t + u }
            }
        }

        input.first().forEachIndexed() { index, _ ->
            epsilon += freqMap[index]!!.entries.maxByOrNull { entry -> entry.value }!!.key
            gamma += freqMap[index]!!.entries.minByOrNull { entry -> entry.value }!!.key

            println("epsilon = $epsilon")
            println("gamma   = $gamma")
        }

        return Integer.parseInt(gamma, 2)*Integer.parseInt(epsilon, 2)
    }

    override fun inputName(): String {
        return "/y2021/input03-1"
    }

    override fun input(): List<String> {
        return readFile(inputName())
    }
}

fun main() {
    Puzzle1().solve()
}