package org.maurezen.aoc.y2021.d05

import java.lang.StringBuilder

/**
 * Assumes square
 */
class FloorMap(private val size: Int) {

    private val vents = Array(size) { IntArray(size) { 0 } }
    private var danger = 0

    private val dangerThreshold = 2

    fun vent(start: Point, end: Point, allowDiagonal: Boolean = false) {
        //horizontal are fine
        if (start.first == end.first) {
            interval(start.second, end.second).forEach {
                vents[start.first][it]++
                if (dangerThreshold == vents[start.first][it]) {
                    danger++
                }
            }
            //vertical are also fine
        } else if (start.second == end.second) {
            interval(start.first, end.first).forEach {
                vents[it][start.second]++
                if (dangerThreshold == vents[it][start.second]) {
                    danger++
                }
            }
        }
        //we don't really care about others atm
    }

    /**
     * an interval between from and to, inclusive, disregarding their comparison
     */
    private fun interval(
        from: Int,
        to: Int
    ) = (if (from > to) (from downTo to) else (from..to))

    fun danger() = danger

    override fun toString(): String {
        val sb = StringBuilder("Ocean floor ${size}x$size:\n")
        repeat(size) {
            i -> repeat(size) { j -> sb.append(renderPoint(j, i))}
            sb.append("\n")
        }
        return sb.toString()
    }

    private fun renderPoint(i: Int, j: Int): String {
        return if (vents[i][j] > 0) vents[i][j].toString() else "."
    }

}

typealias Point = Pair<Int, Int>
typealias Line = Pair<Point, Point>