package org.maurezen.aoc

interface ListPuzzle<T,V>: Puzzle<List<T>, V> {

    override fun dumpInput(input: List<T>) {
        input.forEach(System.out::println)
    }
}