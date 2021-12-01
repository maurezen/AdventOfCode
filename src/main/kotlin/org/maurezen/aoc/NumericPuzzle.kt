package org.maurezen.aoc

import org.maurezen.aoc.utils.readNumbersFromFile

interface NumericPuzzle<T>: Puzzle<Int, T> {

    override fun input(): List<Int> {
        return readNumbersFromFile(inputName())
    }
}