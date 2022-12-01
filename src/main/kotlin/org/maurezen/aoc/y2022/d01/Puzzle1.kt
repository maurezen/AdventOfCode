package org.maurezen.aoc.y2022.d01

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1: ListPuzzle<List<Int>, Int> {
    override fun run(input: List<List<Int>>): Int {
        return input.maxOf(List<Int>::sum)
    }

    override fun inputName(): String {
        return "/y2022/input01-1"
    }

    override fun input(): List<List<Int>> {
        val result = mutableListOf<MutableList<Int>>()
        result.add(mutableListOf())
        return readFile(inputName()).fold(result) { listOfLists, emptyOrNumber ->
            if (emptyOrNumber.trim().isEmpty()) {
                listOfLists.add(mutableListOf())
            } else {
                listOfLists.last().add(Integer.parseInt(emptyOrNumber.trim()))
            }
            listOfLists
        }
    }
}

fun main() {
    Puzzle1().solve()
}