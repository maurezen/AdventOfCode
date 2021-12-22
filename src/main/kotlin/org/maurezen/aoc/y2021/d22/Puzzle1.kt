package org.maurezen.aoc.y2021.d22

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile
import org.maurezen.aoc.y2021.d19.Quadruple

typealias Cube = Quadruple<Boolean, IntRange, IntRange, IntRange>

open class Puzzle1 : ListPuzzle<Cube, Long> {

    private val minCoord = -50
    private val maxCoord = 50
    private val coordSet = (minCoord..maxCoord).toSet()

    override fun run(input: List<Cube>): Long {
        val side = maxCoord - minCoord + 1
        val cube = Array(side) { Array(side) { BooleanArray(side) { false } } }

        input
            .filter { cbe -> listOf(cbe.second, cbe.third, cbe.fourth).all { coordSet.intersect(it).isNotEmpty() } }
            .forEach {
                it.second.forEach { x ->
                    it.third.forEach { y ->
                        it.fourth.forEach { z ->
                            cube[x - minCoord][y - minCoord][z - minCoord] = it.first
                        }
                    }
                }
            }

        return cube.sumOf { plane -> plane.sumOf { row -> row.count { it } } }.toLong()
    }

    override fun inputName(): String {
        return "/input22-1"
    }

    override fun input(): List<Cube> {
        return readStuffFromFile(inputName()) { s ->
            val split = s.split(" ")
            val on = split[0] == "on"
            Pair(on, split[1].split(",").map {
                val numbers = it.subSequence(2, it.length).split("..").map(Integer::parseInt)
                range(numbers[0], numbers[1])
            })
        }.map {
            Quadruple(it.first, it.second[0], it.second[1], it.second[2])
        }
    }

    override fun dumpInput(input: List<Quadruple<Boolean, IntRange, IntRange, IntRange>>) {
        input.forEach {
            println("${if (it.first) "on" else "off"} x=${it.second},y=${it.third},z=${it.fourth}")
        }
    }
}



fun main() {
    Puzzle1().solve()
}
