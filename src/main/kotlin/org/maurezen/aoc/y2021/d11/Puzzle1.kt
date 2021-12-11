package org.maurezen.aoc.y2021.d11

import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.set
import org.maurezen.aoc.y2021.d05.get
import java.util.*

open class Puzzle1 : org.maurezen.aoc.y2021.d09.Puzzle1() {

    private val steps = 100
    private val maxEnergy = 9

    override fun run(input: List<List<Int>>): Int {
        val octos = input.map(List<Int>::toMutableList).toMutableList()
        while (needAnotherStep()) {
            val powerOverwhelming = mutableSetOf<Point>()
            octos.forEachIndexed { i, row ->
                row.forEachIndexed { j, _ ->
                    octos[i][j]++
                    if (row[j] > maxEnergy) {
                        powerOverwhelming.add(Pair(i, j))
                    }
                }
            }

            val queue = ArrayDeque(powerOverwhelming)
            while (queue.isNotEmpty()) {
                val next = queue.removeFirst()

                val neighbours = neighbours(input, next).minus(powerOverwhelming)
                octos[next] = 0
                neighbours.forEach { octos[it]++ }

                val toFlash = neighbours.filter { octos[it] > maxEnergy }
                queue.addAll(toFlash)
                powerOverwhelming.addAll(toFlash)
            }

            processFlashes(powerOverwhelming)
        }
        return score
    }

    protected open fun needAnotherStep(): Boolean {
        return steps > 0
    }

    protected open fun processFlashes(powerOverwhelming: MutableSet<Point>) {
        score += powerOverwhelming.size
    }

    override fun neighbours(input: List<List<Int>>, point: Point): List<Pair<Int, Int>> {
        return mutableSetOf(
            Pair(point.first - 1, point.second),
            Pair(point.first - 1, point.second - 1),
            Pair(point.first - 1, point.second + 1),
            Pair(point.first + 1, point.second),
            Pair(point.first + 1, point.second - 1),
            Pair(point.first + 1, point.second + 1),
            Pair(point.first, point.second - 1),
            Pair(point.first, point.second + 1),
        ).filter { isOnMap(it, input) }
    }

    override fun inputName(): String {
        return "/input11-1"
    }
}

fun main() {
    Puzzle1().solve()
}