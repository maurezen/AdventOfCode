package org.maurezen.aoc.y2021.d02

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readStuffFromFile
import java.util.function.Consumer

open class Puzzle1: Puzzle<Pair<Direction, Int>, Int> {
    override fun run(input: List<Pair<Direction, Int>>): Int {
        var depth = 0
        var path = 0
        input.forEach(Consumer {
            depth += it.first.signY*it.second
            path += it.first.signX*it.second
        })
        return path * depth
    }

    override fun inputName(): String {
        return "/input02-1"
    }

    override fun input(): List<Pair<Direction, Int>> {
        return readStuffFromFile(inputName()) {
            val list = it.split(" ")
            Pair(Direction.valueOf(list[0]), Integer.parseInt(list[1]))
        }
    }
}

fun main() {
    Puzzle1().solve()
}