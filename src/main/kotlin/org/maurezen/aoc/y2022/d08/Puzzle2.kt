package org.maurezen.aoc.y2022.d08

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile
import org.maurezen.aoc.y2021.d05.FloorMap
import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.get
import org.maurezen.aoc.y2021.d05.interval
import java.util.*
import kotlin.math.abs

open class Puzzle2: Puzzle1() {
    override fun run(input: List<List<Int>>): Int {
        println("Leaving visible trees as is, putting underscores for the invisible ones")
        dumpVisibility(input)

        return input.mapIndexed { row, line ->
            line.mapIndexed { column, _ -> visibilityScore(input, Point(row, column)) }.maxOrNull()!!
        }.maxOrNull()!!
    }

    private fun visibilityScore(map: List<List<Int>>, tree: Point): Int {
        return projectTreeOnBorders(map, tree).map { visibilityScore(map, tree, it) }.fold(1) { a,b -> a*b}
    }

    private fun visibilityScore(map: List<List<Int>>, tree: Point, edgeTree: Point): Int {
        val blockingTree = createTreeInterval(tree, edgeTree).minus(tree).firstOrNull { map[it] >= map[tree] } ?: edgeTree
        return distance(tree, blockingTree)
    }

    private fun distance(first: Point, another: Point): Int {
        return if (first.first == another.first) {
            abs(another.second - first.second)
        } else {
            abs(another.first - first.first)
        }
    }

    override fun inputName(): String {
        return "/y2022/input08-1"
//        return "/y2022/input08-1-test"
    }
}

fun main() {
    Puzzle2().solve()
}