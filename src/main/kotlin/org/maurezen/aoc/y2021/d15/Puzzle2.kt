package org.maurezen.aoc.y2021.d15

class Puzzle2: Puzzle1() {

    override fun input(): List<List<Int>> {
        val unscaled = super.input()

        val scaled = unscaled.map { it.toMutableList() }.toMutableList()

        (1..4).forEach {
            unscaled.forEachIndexed { y, _ ->
                scaled.add(scaled[y].map { number -> scale(number, it) }.toMutableList())
            }
        }

        scaled.indices.forEach {
            scaled[it] = scale(scaled[it], 5)
        }

        return scaled
    }

    private fun scale(list: List<Int>, factor: Int): MutableList<Int> {
        val scaled = list.toMutableList()

        (1 until factor).forEach {
            scaled.addAll(list.map { number -> scale(number, it) })
        }

        return scaled
    }

    private fun scale(number: Int, shift: Int) = (number + shift - 1) % 9 + 1
}

fun main() {
    Puzzle2().solve()
}