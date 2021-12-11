package org.maurezen.aoc.y2021.d11

import org.maurezen.aoc.y2021.d05.Point
import org.maurezen.aoc.y2021.d05.set
import org.maurezen.aoc.y2021.d05.get
import java.util.*

open class Puzzle2 : Puzzle1() {

    var steps = 0

    override fun needAnotherStep(): Boolean {
        return 0 == score
    }

    override fun processFlashes(powerOverwhelming: MutableSet<Point>) {
        steps++
        if (100 == powerOverwhelming.size) {
            score = steps
        }
    }
}

fun main() {
    Puzzle2().solve()
}