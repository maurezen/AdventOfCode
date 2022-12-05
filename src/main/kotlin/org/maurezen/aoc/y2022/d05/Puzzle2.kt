package org.maurezen.aoc.y2022.d05

import java.util.*

open class Puzzle2: Puzzle1() {

    override fun inputName(): String {
        return "/y2022/input05-1"
//        return "/y2022/input05-1-test"
    }

    override fun applyMove(position: List<Stack<Char>>, move: Triple<Int, Int, Int>) {
        val holder = Stack<Char>()
        repeat(move.first) {
            holder.push(position[move.second].pop())
        }
        repeat(move.first) {
            position[move.third].push(holder.pop())
        }

    }
}

fun main() {
    Puzzle2().solve()
}