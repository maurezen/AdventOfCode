package org.maurezen.aoc.y2021.d18

class Puzzle2: Puzzle1() {

    override fun run(input: List<SnailNumber>): Int {
        return (input.indices x input.indices)
            .filterNot { it.first == it.second }
            .maxOf { (input[it.first] + input[it.second]).magnitude() }
    }
}

infix fun <V, U> Iterable<V>.x(another: Iterable<U>): Iterable<Pair<V, U>> {
    return this.map { first -> another.map { Pair(first, it) } }.flatten()
}

fun main() {
    Puzzle2().solve()
}