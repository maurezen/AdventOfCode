package org.maurezen.aoc.y2021.d10

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import java.util.*

open class Puzzle1 : ListPuzzle<String, Int> {

    private var score = 0

    private val brackets = mapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))
    private val scores = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))

    override fun run(input: List<String>): Int {
        input.forEach {
            val stack = ArrayDeque<Char>()

            for (c in it.toCharArray()) {
                if (isOpening(c)) {
                    stack.push(c)
                } else if (isClosing(c)) {
                    if (matches(stack.peek(), c)) {
                        stack.pop()
                    } else {
                        onCorruptedString(it, c)
                        break
                    }
                } else {
                    onNonBracketChar(it, c)
                    break
                }
            }

            if (stack.isEmpty()) {
                onCorrectString(it)
            } else {
                onIncompleteString(it, stack)
            }
        }

        return score
    }

    private fun onIncompleteString(string: String, stack: Deque<Char>) {
    }

    private fun onCorrectString(string: String) {
    }

    private fun onNonBracketChar(string: String, c: Char) {
    }

    private fun onCorruptedString(string: String, c: Char) {
        score += scores[c]!!
    }

    private fun isOpening(c: Char): Boolean {
        return brackets.containsKey(c)
    }

    private fun isClosing(c: Char): Boolean {
        return brackets.containsValue(c)
    }

    private fun matches(previous: Char, next: Char): Boolean {
        return next == brackets[previous]
    }

    override fun inputName(): String {
        return "/input10-1"
    }

    override fun input(): List<String> {
        return readFile(inputName())
    }
}

fun main() {
    Puzzle1().solve()
}