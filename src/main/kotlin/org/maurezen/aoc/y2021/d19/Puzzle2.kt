package org.maurezen.aoc.y2021.d19

import org.maurezen.aoc.y2021.d18.x
import space.kscience.kmath.linear.Matrix
import space.kscience.kmath.linear.Point
import space.kscience.kmath.structures.asSequence

class Puzzle2: Puzzle1() {

    override fun run(input: List<ScannerReport>): Int {
        val list = input.toMutableList()

        //index to (scanner_index in scanner_0 coords, rotation to get scanner_index orientation from scanner_0)
        val coords = mutableMapOf(0 to Pair(natural.buildVector(3) { 0 }, neutral))

        while (coords.size < list.size) {
            run outer@{
                (list.indices x list.indices)
                    .filterNot { it.first == it.second || !coords.contains(it.first) || coords.contains(it.second) }
                    .forEach {
                        println("looking at scanners ${it.first} and ${it.second}")
                        val intersect = list[it.first].intersect(list[it.second])
                        if (intersect.first.size >= 12) {
                            val (_, secondCoordInFirst) =
                                list[it.first].union(
                                    list[it.second],
                                    intersect.second,
                                    intersect.third,
                                    intersect.fourth
                                )

                            val firstCoord = coords[it.first]!!.first
                            val firstRotation = coords[it.first]!!.second

                            with(natural) {
                                val secondRotation = firstRotation dot intersect.second
                                val secondCoord = firstRotation dot secondCoordInFirst
                                coords[it.second] = Pair(firstCoord + secondCoord, secondRotation)
                            }

                            println("${list.size - coords.size} scanner(s) left to process")
                            // gonna burn in hell for this, but at this point into day 19 idgaf
                            return@outer
                        }
                    }
            }
        }

        return score(coords)
    }

    private fun score(coords: Map<Int, Pair<Point<Int>, Matrix<Int>>>): Int {
        val points = coords.values.map { it.first }
        return (points x points)
            .filterNot { it.first == it.second }
            .maxOf { manhattanDistance(it.first, it.second) }
    }

    private fun manhattanDistance(first: Point<Int>, second: Point<Int>): Int {
        return with (natural) { first - second }.asSequence().sumOf(Math::abs)
    }
}

fun main() {
    Puzzle2().solve()
}