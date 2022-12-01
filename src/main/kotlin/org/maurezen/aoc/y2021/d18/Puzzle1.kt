package org.maurezen.aoc.y2021.d18

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readStuffFromFile

open class Puzzle1 : ListPuzzle<SnailNumber, Int> {

    override fun run(input: List<SnailNumber>): Int {
        val total = input.reduce { a, b -> a + b }

        dumpInput(listOf(total))

        return total.magnitude()
    }

    override fun inputName(): String {
        return "/y2021/input18-1"
    }

    override fun input(): List<SnailNumber> {
        return readStuffFromFile(inputName()) { (this.parse(it) as SnailNode).value }
    }

    private fun parse(s: CharSequence): Node {
        if (s[0] != '[') {
            return IntNode(Integer.parseInt(s.toString()))
        }

        val shaved = s.subSequence(1, s.length - 1)
        var depth = 0
        shaved.forEachIndexed { index, char ->
            when(char) {
                '[' -> depth++
                ']' -> depth--
                ',' -> {
                    //means we found the splitter for the outermost pair
                    if (depth == 0) {
                        return SnailNode(
                            SnailNumber(
                                parse(shaved.subSequence(0, index)),
                                parse(shaved.subSequence(index + 1, shaved.length))
                            )
                        )
                    }
                }
                else -> {}
            }
        }
        throw RuntimeException("Not supposed to be here, like, ever")
    }

    override fun dumpInput(input: List<SnailNumber>) {
        input.forEach(::println)
    }
}

fun main() {
    Puzzle1().solve()
}
