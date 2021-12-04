package org.maurezen.aoc

interface Puzzle<T, V> {

    /**
     * Runs this puzzle and returns a result
     */
    fun run(input: T): V

    fun inputName(): String

    fun input(): T

    fun dumpInput(input: T)

    fun solve() {
        val input = input()
        dumpInput(input)

        println("${this::class.simpleName} answer = ${run(input)}")
    }
}