package org.maurezen.aoc.y2022.d08

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile
import org.maurezen.aoc.y2021.d05.FloorMap
import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.get
import org.maurezen.aoc.y2021.d05.interval

open class Puzzle1: ListPuzzle<List<Int>, Int> {
    override fun run(input: List<List<Int>>): Int {
        println("Leaving visible trees as is, putting underscores for the invisible ones")
        dumpVisibility(input)

        return input.mapIndexed { row, line ->
            line.filterIndexed { column, _ -> isVisible(input, Point(row, column)) }.count()
        }.sum()
    }

    protected fun dumpVisibility(input: List<List<Int>>) {
        input.mapIndexed { row, line ->
            println(line.mapIndexed { column, height ->
                if (isVisible(
                        input,
                        Point(row, column)
                    )
                ) height.toString() else "_"
            })
        }
    }

    private fun isVisible(map: List<List<Int>>, tree: Point): Boolean {
        return projectTreeOnBorders(map, tree).any { visibleFromOutside(map, tree, it) }
    }

    protected fun projectTreeOnBorders(map: List<List<Int>>, tree: Point): List<Point> {
        return listOf(
            Point(tree.first, 0),
            Point(0, tree.second),
            Point(tree.first, map[0].lastIndex),
            Point(map.lastIndex, tree.second)
        )
    }

    private fun visibleFromOutside(map: List<List<Int>>, insideTree: Point, edgeTree: Point): Boolean {
        return createTreeInterval(insideTree, edgeTree).minus(insideTree).all { map[it] < map[insideTree] }
    }

    //assumes the points to be on the same row/column
    protected fun createTreeInterval(insideTree: Point, edgeTree: Point): List<Point> {
        return if (insideTree.first == edgeTree.first) {
            interval(insideTree.second, edgeTree.second).map { Point(insideTree.first, it) }
        } else {
            interval(insideTree.first, edgeTree.first).map { Point(it, insideTree.second) }
        }
    }

    override fun inputName(): String {
        return "/y2022/input08-1"
//        return "/y2022/input08-1-test"
    }

    override fun input(): List<List<Int>> {
        //this is a no-go in prod, but who cares for a 100x100 grid
        return readStuffFromFile(inputName()) { it.map { char -> Integer.parseInt(char.toString())} }
    }
}

fun main() {
    Puzzle1().solve()
}