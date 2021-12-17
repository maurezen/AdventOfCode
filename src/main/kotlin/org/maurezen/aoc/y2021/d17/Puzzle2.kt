package org.maurezen.aoc.y2021.d17

import org.maurezen.aoc.y2021.d05.Point

class Puzzle2: Puzzle1() {

    override fun score(goodStarts: MutableList<Pair<Point, Int>>): Int {
        return goodStarts.size
    }
}

fun main() {
    Puzzle2().solve()
}