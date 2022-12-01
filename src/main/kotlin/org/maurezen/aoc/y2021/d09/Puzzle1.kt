package org.maurezen.aoc.y2021.d09

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readStuffFromFile
import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.get

open class Puzzle1 : Puzzle<List<List<Int>>, Int> {

    var score = 0

    override fun run(input: List<List<Int>>): Int {
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                val point = Pair(i, j)
                val neighbours = neighbours(input, point)

                if (isLowPoint(input, point, neighbours)) {
                    processLowPoint(input, point)
                }
            }
        }
        return score
    }

    protected open fun neighbours(
        input: List<List<Int>>,
        point: Point
    ) = mutableSetOf(
        Pair(point.first - 1, point.second),
        Pair(point.first + 1, point.second),
        Pair(point.first, point.second - 1),
        Pair(point.first, point.second + 1)
    ).filter { isOnMap(it, input) }

    protected fun isOnMap(
        it: Point,
        input: List<List<Int>>
    ) = it.first >= 0 && it.first < input.size && it.second >= 0 && it.second < input.first().size

    protected open fun processLowPoint(input: List<List<Int>>, point: Pair<Int, Int>) {
        score += 1 + input[point]
    }

    private fun isLowPoint(input: List<List<Int>>, point: Point, neighbours: List<Point>): Boolean {
        return input[point] < neighbours.minOf { input[it] }
    }

    override fun inputName(): String {
        return "/y2021/input09-1"
    }

    override fun input(): List<List<Int>> {
        return readStuffFromFile(inputName()) { s -> s.toCharArray().map { Integer.parseInt(it.toString()) } }
    }

    override fun dumpInput(input: List<List<Int>>) {
        input.forEach { println(it.joinToString("")) }
    }
}

fun main() {
    Puzzle1().solve()
}