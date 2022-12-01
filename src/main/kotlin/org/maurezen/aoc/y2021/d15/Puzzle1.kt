package org.maurezen.aoc.y2021.d15

import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.set
import org.maurezen.aoc.y2021.d05.get
import java.util.*
import kotlin.Comparator

open class Puzzle1 : org.maurezen.aoc.y2021.d09.Puzzle1() {

    override fun run(input: List<List<Int>>): Int {
        val end = Pair(input.size - 1, input.first().size - 1)
        val distancesFromStart = input.map { it.map { Int.MAX_VALUE }.toMutableList() }.toMutableList()
        distancesFromStart[0][0] = 0

        val visited = mutableSetOf<Point>()

        val yetToVisit = PriorityQueue<Point>(Comparator.comparing { distancesFromStart[it] })
        yetToVisit.addAll(input.mapIndexed { x, list -> list.mapIndexed { y, _ -> Pair(x, y) } }.flatten())

        while (yetToVisit.peek() != end) {
            val next = yetToVisit.poll()

            val neighbours = neighbours(input, next).minus(visited)
            neighbours.forEach {
                val newDistance = distancesFromStart[next] + input[it]
                if (newDistance < distancesFromStart[it]) {
                    yetToVisit.remove(it)
                    distancesFromStart[it] = newDistance
                    yetToVisit.offer(it)
                }
            }

            visited.add(next)
        }

        return distancesFromStart[end]
    }

    override fun inputName(): String {
        return "/y2021/input15-1"
    }
}

fun main() {
    Puzzle1().solve()
}