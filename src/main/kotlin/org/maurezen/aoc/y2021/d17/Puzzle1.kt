package org.maurezen.aoc.y2021.d17

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readStuffFromFile
import org.maurezen.aoc.y2021.d05.Point

open class Puzzle1 : Puzzle<Pair<Point, Point>, Int> {

    //it appears that more steps still might be needed for pt2
    private val steps = 31*8

    override fun run(input: Pair<Point, Point>): Int {
        val goodStarts = mutableListOf<Pair<Point, Int>>()

        //we assume positive vy, otherwise max is 0
        //we also assume x of the same sign as x (fortunately it is >0)
        //we also assume target y < 0
        (0..input.first.second).forEach { vx ->
            range(input.second.first).forEach { vy ->
                repeat(steps) {
                    val x = x(vx, it)
                    val y = y(vy, it)

                    if (input.first.first <= x && x <= input.first.second
                        && input.second.second >= y && y >= input.second.first) {
                            goodStarts.add(Pair(Pair(vx, vy), it))
                            return@forEach
                    }
                }
            }
        }

        return score(goodStarts)
    }

    protected open fun score(goodStarts: MutableList<Pair<Point, Int>>) =
        goodStarts.maxOf { maxY(it.first.second, it.second) }

    private fun range(target: Int): IntRange {
        return if (target < 0) (target..-target) else (-target..target)
    }

    private fun x(v0: Int, t: Int): Int {
        return if (v0 <= t) v0 * (v0 + 1) / 2 else (v0 * t - t * (t - 1) / 2)
    }

    private fun y(v0: Int, t: Int): Int {
        return t * v0 - t * (t - 1) / 2
    }

    private fun maxY(v0: Int, maxT: Int): Int {
        return x(v0, maxT)
    }

    override fun inputName(): String {
        return "/y2021/input17-1"
    }

    override fun input(): Pair<Point, Point> {
        return readStuffFromFile(inputName()) {
            val coords = it.substring("target area: ".length).split(", ")
            val x = coords.first().substring("x=".length).split("..").map(Integer::parseInt)
            val y = coords.last().substring("y=".length).split("..").map(Integer::parseInt)

            Pair(Pair(x.first(), x.last()), Pair(y.first(), y.last()))
        }.first()
    }

    override fun dumpInput(input: Pair<Point, Point>) {
        println("target area: x=${input.first.first}..${input.first.second}, y=${input.second.first}..${input.second.second}")
    }
}

fun main() {
    Puzzle1().solve()
}
