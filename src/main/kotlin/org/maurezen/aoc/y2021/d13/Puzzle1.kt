package org.maurezen.aoc.y2021.d13

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.y2021.d05.Point

open class Puzzle1 : Puzzle<Pair<Array<BooleanArray>, List<Pair<Int, Int>>>, Int> {

    override fun run(input: Pair<Array<BooleanArray>, List<Pair<Int, Int>>>): Int {
        var dots = input.first
        val folds = input.second.toMutableList()
        while (folds.isNotEmpty()) {
            dots = fold(dots, folds.removeFirst())

            dumpInput(Pair(dots, folds))
            break
        }
        return countDots(dots)
    }

    override fun inputName(): String {
        return "/input13-1"
    }

    private val foldPrefix = "fold along "

    override fun input(): Pair<Array<BooleanArray>, MutableList<Point>> {
        val dotsList = mutableListOf<Point>()
        val folds = mutableListOf<Point>()
        readFile(inputName()).forEach {
            when {
                it.isEmpty() -> {}
                it.startsWith(foldPrefix) -> {
                    val line = it.substring(foldPrefix.length)
                    val coord = line.substring(2)
                    folds.add(
                        if (line[0] == 'x') Pair(Integer.parseInt(coord), 0) else Pair(0, Integer.parseInt(coord))
                    )
                }
                else -> {
                    val split = it.split(",")
                    //mark (x, y)
                    dotsList.add(Pair(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
                }
            }
        }

        val maxX = dotsList.maxOf { it.first }
        val maxY = dotsList.maxOf { it.second }

        val dots = Array(maxX + 1) { BooleanArray(maxY + 1)}
        dotsList.forEach { dots[it] = true }

        return Pair(dots, folds)
    }

    override fun dumpInput(input: Pair<Array<BooleanArray>, List<Pair<Int, Int>>>) {
        input.first.first().forEachIndexed { y, _ ->
            println(input.first.mapIndexed { x, _ -> if (input.first[x][y]) "#" else "." }.joinToString(""))
        }

        println()

        input.second.forEach { println("$foldPrefix${if (it.first == 0) "y" else "x"}=${if (it.first == 0) it.second else it.first}") }

        println("#dots: ${countDots(input.first)}")
    }

    private fun countDots(dots: Array<BooleanArray>): Int {
        return dots.sumOf { it.filter { dot -> dot }.count() }
    }

    private fun fold(dots: Array<BooleanArray>, where: Point): Array<BooleanArray> {
        return if (where.first == 0) foldY(dots, where.second) else foldX(dots, where.first)
    }

    private fun foldX(dots: Array<BooleanArray>, where: Int): Array<BooleanArray> {
        val sizeX = dots.size
        assert( where == sizeX / 2) { "We expect to fold the paper exactly in half" }
        return Array(where) { x -> BooleanArray(dots.first().size) { y ->
            dots[x][y] || dots[sizeX - x - 1][y]
        } }
    }

    private fun foldY(dots: Array<BooleanArray>, where: Int): Array<BooleanArray> {
        val sizeY = dots.first().size
        assert( where == sizeY / 2) { "We expect to fold the paper exactly in half" }
        return Array(dots.size) { x -> BooleanArray(where) { y ->
            dots[x][y] || dots[x][sizeY - y - 1]
        } }
    }
}

private operator fun Array<BooleanArray>.set(it: Pair<Int, Int>, value: Boolean) {
    this[it.first][it.second] = value
}

fun main() {
    Puzzle1().solve()
}