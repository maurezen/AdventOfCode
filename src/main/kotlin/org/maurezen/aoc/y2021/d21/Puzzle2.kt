package org.maurezen.aoc.y2021.d21

class Puzzle2: Puzzle1() {

    // 3d3 distribution: roll to outcomes
    private val rolls3d3 = IntArray(10) { mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)[it] ?: 0 }

    // 10 turns ought to be enough for everyone
    private val turnsToPlay = 10

    override fun run(input: List<Int>): Long {
        val start = Pair(input.first() - 1, input.last() - 1)

        //single state object per player
        //state: turn to position to score to universes
        val stateFirst = Array(1 + turnsToPlay) { Array(board.size) { LongArray(target() + 1) } }
        stateFirst[0][start.first][0] = 1

        //state: turn to position to score to universes
        val stateSecond = Array(1 + turnsToPlay) { Array(board.size) { LongArray(target() + 1) } }
        stateSecond[0][start.second][0] = 1

        listOf(stateFirst, stateSecond).forEach { state ->
            //we don't want to progress on the last turn
            state.sliceArray(0 until state.lastIndex).forEachIndexed { turn, positions ->
                positions.forEachIndexed { position, scores ->
                    //we don't care much for progress after score target either
                    scores.sliceArray(0 until scores.lastIndex).forEachIndexed { score, universes ->
                        rolls3d3.forEachIndexed { roll, universesSingleRoll ->
                            state[turn + 1][position(position, roll)][(score + score(position, roll)).coerceAtMost(target())] += universes * universesSingleRoll
                        }
                    }
                }
            }
        }

        //p1 wins: sum over turns where on turn X p1 has target and (=multiply) on turn X-1 p2 has < target
        val p1verses = stateFirst
            //there's no winning on the 1st turn
            .sliceArray(1..stateFirst.lastIndex).mapIndexed { turnMinus1, positions ->
            //universes where p1 won on this turn
            //times universes where p2 has NOT won on turn minus 1
            positions.sumOf { scores ->
                //universes where p1 won on this turn
                val universes = scores[21]
                //times universes where p2 has NOT won on turn minus 1
                universes * stateSecond[turnMinus1].sumOf { scores2 -> scores2.sliceArray(0 until scores2.lastIndex).sum() }
            }
        }.sum()

        //p2 wins:
        val p2verses = stateSecond.sliceArray(0..stateFirst.lastIndex).mapIndexed { turn, positions ->
            //universes where p2 won on this turn
            //times universes where p1 has NOT won on this turn
            positions.sumOf { scores ->
                //universes where p2 won on this turn
                val universes = scores[21]
                //times universes where p1 has NOT won on this turn
                universes * stateFirst[turn].sumOf { scores1 -> scores1.sliceArray(0 until scores1.lastIndex).sum() }
            }
        }.sum()

        return maxOf(p1verses, p2verses)
    }

    private fun dumpState(state: Array<Array<LongArray>>) {
        state.forEachIndexed{ turn, positions  ->
            println("scores for turn $turn")
            println("                            scores: ${(0..positions[0].lastIndex).joinToString(","){ it.toString().padStart(2)}}")
            positions.forEachIndexed { position, scores ->
                println("for position $position scores to universes: ${scores.joinToString(",") { it.toString().padStart(2) }}")
            }
        }
    }

    private fun position(position: Int, roll: Int): Int {
        return (position + roll) % board.size
    }

    private fun score(current: Int, roll: Int): Int {
        return board[position(current, roll)]
    }

    override fun target() = 21

}

fun main() {
    Puzzle2().solve()
}