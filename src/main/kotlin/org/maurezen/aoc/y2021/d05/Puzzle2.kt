package org.maurezen.aoc.y2021.d05

class Puzzle2: Puzzle1() {

    override fun applyVent(floor: FloorMap, vent: Line) {
        floor.vent(vent.first, vent.second, true)
    }
}

fun main() {
    Puzzle2().solve()
}