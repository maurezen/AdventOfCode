package org.maurezen.aoc

import org.maurezen.aoc.utils.readCSVNumbersFromFile

interface NumericCSVPuzzle<T>: ListPuzzle<Int, T> {

    override fun input(): List<Int> {
        return readCSVNumbersFromFile(inputName())
    }

    override fun dumpInput(input: List<Int>) {
        println(input.joinToString(","))
    }
}
