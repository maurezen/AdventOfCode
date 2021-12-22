package org.maurezen.aoc.y2021.d18

infix fun <V, U> Iterable<V>.x(another: Iterable<U>): Iterable<Pair<V, U>> {
    return this.map { first -> another.map { Pair(first, it) } }.flatten()
}

infix fun <V, U, W> Iterable<Pair<V, U>>.X(another: Iterable<W>): Iterable<Triple<V, U, W>> {
    return this.map { first -> another.map { Triple(first.first, first.second, it) } }.flatten()
}