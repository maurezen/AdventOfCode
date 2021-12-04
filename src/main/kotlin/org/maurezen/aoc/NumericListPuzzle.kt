package org.maurezen.aoc

import org.maurezen.aoc.utils.readNumbersFromFile

interface NumericListPuzzle<T>: ListPuzzle<Int, T> {

    override fun input(): List<Int> {
        return readNumbersFromFile(inputName())
    }
}