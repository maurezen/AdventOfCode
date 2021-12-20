package org.maurezen.aoc.y2021.d20

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

open class Puzzle1 : Puzzle<Pair<String, List<String>>, Int> {

    override fun run(input: Pair<String, List<String>>): Int {
        var image = input.second
        var dflt = '.'

        repeat(steps()) {
            val enhanced = enhance(image, input.first, dflt)
            image = enhanced.first
            dflt = enhanced.second
        }

        dumpInput(Pair(input.first, image))
        return score(image)
    }

    protected open fun steps() = 2

    private fun enhance(image: List<String>, algorithm: String, dflt: Char): Pair<List<String>, Char> {
        val result = mutableListOf<String>()

        (-4 until image.size + 2).forEach { line ->
            val builder = StringBuilder()
            (-4 until image.first().length + 2).forEach { position ->
                val surroundingPixels = lowerRightPixels(image, line, position, dflt)
                val convertToNumber = convertToNumber(surroundingPixels)
                builder.append(algorithm[convertToNumber])
            }
            result.add(builder.toString())
        }

        return Pair(result, algorithm[convertToNumber(lowerRightPixels(image, -42, -42, dflt))])
    }

    private fun convertToNumber(surroundingPixels: List<CharSequence>): Int {
        return Integer.parseInt(surroundingPixels.joinToString("").map {
            when (it) {
                '#' -> 1
                '.' -> 0
                else -> throw IllegalArgumentException("illegal character: $it")
            }
        }.joinToString(""), 2)
    }

    private fun lowerRightPixels(image: List<String>, line: Int, position: Int, dflt: Char): List<CharSequence> {
        return listOf(
            stringOrEmpty(image, line, dflt.toString()).subSequenceOrDefault(position, position + 3, dflt),
            stringOrEmpty(image, line + 1, dflt.toString()).subSequenceOrDefault(position, position + 3, dflt),
            stringOrEmpty(image, line + 2, dflt.toString()).subSequenceOrDefault(position, position + 3, dflt)
        )
    }

    private fun String.subSequenceOrDefault(startIndex: Int, endIndex: Int, dflt: Char): String {
        return (startIndex until endIndex).map { if (it in indices) this[it] else dflt }.joinToString("")
    }

    private fun stringOrEmpty(image: List<String>, line: Int, dflt: String): String {
        return if (line in image.indices) image[line] else dflt.repeat(image.first().length)
    }

    private fun score(image: List<String>): Int {
        return image.sumOf { it.count { c -> '#' == c} }
    }

    override fun inputName(): String {
        return "/input20-1"
    }

    override fun input(): Pair<String, List<String>> {
        val lines = readFile(inputName())

        return Pair(lines.first(), lines.subList(2, lines.size))
    }

    override fun dumpInput(input: Pair<String, List<String>>) {
        println(input.first)
        println()
        input.second.forEach(::println)
    }
}

fun main() {
    Puzzle1().solve()
}
