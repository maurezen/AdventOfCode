package org.maurezen.aoc.y2021.d04

/**
 * Assuming square, non-empty boards
 * Assuming no duplicate numbers
 *
 * i,j indexing:
 * 0 1 2 3 4
 * 1
 * 2
 * 3
 * 4
 */
class Board(private val board: List<List<Int>>) {

    private val marks = Array(board.size) { BooleanArray(board.size) { false } }
    private var bingo = false

    fun mark(number: Int): Boolean {
        board.forEachIndexed{ i, column ->
            column.forEachIndexed { j, boardNumber ->
                if (boardNumber == number) return mark(i, j)
            }
        }
        return bingo
    }

    private fun mark(i: Int, j: Int): Boolean {
        marks[i][j] = true
        if (!bingo) {
            var bingoRow = true
            var bingoColumn = true
            (marks.indices).forEach {
                bingoRow = bingoRow && marks[i][it]
                bingoColumn = bingoColumn && marks[it][j]
            }
            bingo = bingoRow || bingoColumn
        }

        return bingo
    }

    fun score(winningNumber: Int) : Int {
        return winningNumber *
                board
                    .mapIndexed { i, column -> column.filterIndexed { j, _ -> !marks[i][j] }.sum() }
                    .sum()
    }

    override fun toString(): String {
        return "Board(board=$board)"
    }

}