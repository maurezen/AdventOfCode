package org.maurezen.aoc.y2021.d02

import java.util.function.Consumer

class Puzzle2: Puzzle1() {
    override fun run(input: List<Pair<Direction, Int>>): Int {
        var aim = 0
        var depth = 0
        var path = 0
        input.forEach(Consumer {
            aim += it.first.signY*it.second
            path += it.first.signX*it.second
            depth += it.first.signX*it.second*aim
        })
        return path * depth
    }
}

fun main() {
    Puzzle2().solve()
}