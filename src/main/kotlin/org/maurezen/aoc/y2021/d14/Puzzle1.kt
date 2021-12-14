package org.maurezen.aoc.y2021.d14

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile

open class Puzzle1 : Puzzle<Pair<String, List<Pair<String, String>>>, Int> {

    override fun run(input: Pair<String, List<Pair<String, String>>>): Int {
        var template = input.first
        val rules = input.second.toMap()
        repeat(steps()) {
            template = apply(template, rules)
        }
        return score(template)
    }

    private fun steps() = 10

    private fun apply(template: String, rules: Map<String, String>): String {
        return template.first() + template.windowed(2).joinToString("") { rules[it] + it.last() }
    }

    private fun score(polymer: String): Int {
        val sizes = polymer.toCharArray().groupBy { it }.mapValues { (_, v) -> v.size }.values
        return sizes.maxOf { v -> v } - sizes.minOf { v -> v }
    }

    override fun inputName(): String {
        return "/input14-1"
    }

    override fun input(): Pair<String, List<Pair<String, String>>> {
        val lines = readFile(inputName()).toMutableList()

        val template = lines.removeFirst()
        lines.removeFirst()

        val rules = lines.map { it.split(" -> ") }.map { Pair(it[0], it[1]) }
        return Pair(template, rules)
    }

    override fun dumpInput(input: Pair<String, List<Pair<String, String>>>) {
        println(input.first)
        println()
        input.second.forEach { println("${it.first} -> ${it.second}") }
    }
}

private operator fun Array<BooleanArray>.set(it: Pair<Int, Int>, value: Boolean) {
    this[it.first][it.second] = value
}

fun main() {
    Puzzle1().solve()
}