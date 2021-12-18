package org.maurezen.aoc

import kotlin.system.measureNanoTime

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

        var result: V
        val time = measureNanoTime { result = run(input) }
        println("${this::class.simpleName} answer = $result in $time ns (${time/1000000} ms)")
    }
}