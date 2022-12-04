package org.maurezen.aoc.y2022.d04

open class Puzzle2: Puzzle1() {

    override fun inputName(): String {
        return "/y2022/input04-1"
//        return "/y2022/input04-1-test"
    }

    override fun filter(first: IntRange, second: IntRange): Boolean {
        return first.any(second::contains) || second.any(first::contains)
    }
}

fun main() {
    Puzzle2().solve()
}