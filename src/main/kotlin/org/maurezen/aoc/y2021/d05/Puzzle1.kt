package org.maurezen.aoc.y2021.d05

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1: Puzzle<Pair<Int, List<Line>>, Int> {
    /**
     * Assuming equal string lengths
     * Assuming non-empty input
     */
    override fun run(input: Pair<Int, List<Line>>): Int {
        val floor = FloorMap(input.first)

        input.second.forEach { floor.vent(it.first, it.second) }

        println(floor.toString())

        return floor.danger()
    }

    override fun inputName(): String {
        return "/input05-1"
    }

    override fun input(): Pair<Int, List<Line>> {
        val strings = readFile(inputName())

        val lines = mutableListOf<Line>()

        strings.map(this::Line).forEach { lines.add(it) }

        val size = 1 + lines.maxOf { maxOf(it.first.first, it.first.second, it.second.first, it.second.second) }

        return Pair(size, lines)
    }

    override fun dumpInput(input: Pair<Int, List<Line>>) {
        println("size = ${input.first}")
        input.second.forEach{ println("${it.first.first},${it.first.second} -> ${it.second.first},${it.second.second}") }
    }

    private fun Line(s: String): Line {
        val points = s.split(" -> ")
        return Pair(Point(points[0]), Point(points[1]))
    }

    private fun Point(s: String): Point {
        val coords = s.split(",")
        return Pair(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]))
    }
}

fun main() {
    Puzzle1().solve()
}