package org.maurezen.aoc.y2021

import org.maurezen.aoc.utils.readNumbersFromFile

interface NumericPuzzle<T>: Puzzle<Int, T> {

    override fun input(): List<Int> {
        return readNumbersFromFile(inputName())
    }
}