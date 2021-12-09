package org.maurezen.aoc.y2021.d09

import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.get
import java.util.*

class Puzzle2: Puzzle1() {

    private val basins = PriorityQueue(compareBy(Set<Point>::size))
    private val k = 3
    private val maxBasinHeight = 8

    override fun processLowPoint(input: List<List<Int>>, point: Pair<Int, Int>) {
        val basin = mapBasin(input, point)
        if (basins.size < k) {
            basins.offer(basin)
        } else if (basin.size > basins.peek().size) {
            basins.poll()
            basins.offer(basin)
        }
        score = basins.take(3).map(Set<Point>::size).reduce { a, b -> a*b}
    }

    private fun mapBasin(input: List<List<Int>>, lowPoint: Pair<Int, Int>): Set<Point> {
        val queue = neighbours(input, lowPoint).filter { maxBasinHeight >= input[it] }.toMutableList()
        val basin = mutableSetOf(lowPoint)

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()

            val neighbours = neighbours(input, next).filter { maxBasinHeight >= input[it] }
            val neighboursWithin = basin.intersect(neighbours)

            if (neighboursWithin.any { input[it] < input[next] }) {
                basin.add(next)
                queue.addAll(neighbours.minus(basin))
            }
        }

        return basin
    }
}

fun main() {
    Puzzle2().solve()
}