package org.maurezen.aoc.y2021.d14

import java.math.BigInteger
import java.util.function.BiFunction
import kotlin.math.sin

class Puzzle2: Puzzle1() {

    override fun run(input: Pair<String, List<Pair<String, String>>>): Long {
        var template = bigrammize(input.first)
        val rules = input.second.toMap()
        repeat(steps()) {
            template = apply(template, rules)
        }
        return score(template, input.first)
    }


    private fun apply(template: Bigrams, rules: Map<String, String>): Bigrams {
        val next = mutableMapOf<String, Long>()
        template.forEach { (bigram, count) ->
            next.addOrPut(bigram.first() + rules[bigram]!!, count)
            next.addOrPut(rules[bigram]!! + bigram.last() , count)
        }
        return next
    }

    private fun bigrammize(template: String): Bigrams {
        return template
            .windowed(2)
            .groupBy { it }
            .mapValues { (_, v) -> v.size.toLong() }
            .toMutableMap()
    }

    private fun score(what: Bigrams, reference: String): Long {
        val singles = mutableMapOf<Char, Long>()
        what.forEach { (bigram, count) ->
            singles.addOrPut(bigram.first(), count)
            singles.addOrPut(bigram.last(), count)
        }
        singles.addOrPut(reference.first(), 1)
        singles.addOrPut(reference.last(), 1)
        return singles.values.maxOf { it/2 } - singles.values.minOf { it/2 }
    }

    override fun steps() = 40
}

typealias Bigrams = MutableMap<String, Long>

fun <K> MutableMap<K, Long>.addOrPut(key: K, value: Long) {
    merge(key, value) { a, b -> a + b}
}

fun main() {
    Puzzle2().solve()
}