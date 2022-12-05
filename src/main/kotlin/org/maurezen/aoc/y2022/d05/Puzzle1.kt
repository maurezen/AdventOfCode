package org.maurezen.aoc.y2022.d05

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.splitByEmptyString
import java.util.*
import java.util.regex.Pattern

open class Puzzle1: Puzzle<Pair<List<Stack<Char>>, List<Triple<Int, Int, Int>>>, List<Char>> {
    override fun run(input: Pair<List<Stack<Char>>, List<Triple<Int, Int, Int>>>): List<Char> {
        //slot 0: [] - for the ease of indexing
        //slot 1: [Z, N]
        //slot 2: [M, C, D]
        //slot 3: [P]
        val startingPosition = input.first
        // move X from Y to Z
        val moves = input.second

        moves.forEach { applyMove(startingPosition, it)}

        dumpInput(input)

        return startingPosition.map { if (it.empty()) '_' else it.peek() }
    }

    /**
     * Mutilates the position, beware
     */
    protected open fun applyMove(position: List<Stack<Char>>, move: Triple<Int, Int, Int>) {
        repeat(move.first) {
            position[move.third].push(position[move.second].pop())
        }
    }

    override fun inputName(): String {
        return "/y2022/input05-1"
//        return "/y2022/input05-1-test"
    }

    override fun input(): Pair<List<Stack<Char>>, List<Triple<Int, Int, Int>>> {
        val blocks = splitByEmptyString(readFile(inputName()))

        return Pair(parseInitialStacks(blocks[0]), parseMoves(blocks[1]))
    }

    private fun parseMoves(moves: List<String>): List<Triple<Int, Int, Int>> {
        val pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)")
        return moves.map {
            val matcher = pattern.matcher(it)
            matcher.find()
            Triple(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
            )
        }
    }

    //    [D]
    //[N] [C]
    //[Z] [M] [P]
    // 1   2   3
    // string[1]. string[5]. string[9]
    // in total length/4 will get us the number
    private fun parseInitialStacks(list: List<String>): List<Stack<Char>> {
        val result = mutableListOf<Stack<Char>>()
        result.add(Stack())

        repeat((0..list[list.size - 2].length / 4).count()) {
            result.add(Stack())
        }

        (list.lastIndex - 1 downTo 0).forEach { lineIndex ->
            val line = list[lineIndex]
            (0 .. (line.length / 4)).forEach {
                if (!line[4 * it + 1].isWhitespace()) {
                    result[it+1].add(line[4 * it + 1])
                }
            }
        }

        return result
    }

    override fun dumpInput(input: Pair<List<Stack<Char>>, List<Triple<Int, Int, Int>>>) {
        input.first.forEachIndexed() { index, stack -> println("slot $index: $stack") }
        input.second.forEach(System.out::println)
    }


}

fun main() {
    Puzzle1().solve()
}