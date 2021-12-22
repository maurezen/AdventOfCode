package org.maurezen.aoc.y2021.d22

fun IntRange.size(): Int {
    return last - first + 1
}

fun IntRange.intersect(another: IntRange): IntRange {
    return IntRange(kotlin.math.max(first, another.first), kotlin.math.min(last, another.last))
}

fun IntRange.contains(another: IntRange): Boolean {
    return first <= another.first && another.last <= last
}

operator fun IntRange.minus(another: IntRange): List<IntRange> {
    val intersection = intersect(another)
    return when {
        intersection.isEmpty() -> {
            listOf(this)
        }
        contains(another) -> {
            listOf(IntRange(first, another.first - 1), IntRange(another.last + 1, last))
        }
        another.contains(this) -> {
            emptyList()
        }
        intersection.first <= first -> {
            listOf(IntRange(intersection.last + 1, last))
        }
        else -> {
            listOf(IntRange(first, intersection.first - 1))
        }
    }
}

//assumes another contained in this
fun IntRange.split(another: IntRange): List<IntRange> {
    assert(contains(another)) { "Expecting to split on a sub-interval only, tried to split $this on $another instead"}
    return listOf(minus(another), listOf(another)).flatten()
}

fun range(from: Int, to: Int): IntRange {
    return if (from < to) (from..to) else (to..from)
}