package org.maurezen.aoc

interface Puzzle<T, V> {

    /**
     * Runs this puzzle and returns a result
     */
    fun run(input: List<T>): V

    fun inputName(): String

    fun input(): List<T>

    fun solve() {
        val input = input()
        input.forEach(System.out::println)

        println("${this::class.simpleName} answer = ${run(input)}")
    }
}