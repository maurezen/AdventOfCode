package org.maurezen.aoc.y2021.d12

class Puzzle2: Puzzle1() {
    //this is an atrocious linear check but it works
    //maintaining a more complex data structure
    //(path + a set of small caves visited + a link to a small cave visited twice)
    //would have made this constant
    override fun acceptable(passage: Passage, nextPath: List<Passage>): Boolean {
        val newPath = nextPath.toMutableList()
        newPath.add(passage)
        return passage.to.big
                // not start
                || ( passage.to != nextPath.first().from
                    // visiting a cave twice is alright
                    && atMostOneSmallVisitedTwice(newPath))
    }

    private fun atMostOneSmallVisitedTwice(path: List<Passage>): Boolean {
        val grouped = path.map(Passage::to).filterNot { it.big }.groupBy { it }.toMutableMap()

        val sizes = grouped.values.map(List<Cave>::size)
        return sizes.filter { 2 == it }.count() < 2 && sizes.maxOrNull() ?: -1 < 3
    }


}

fun main() {
    Puzzle2().solve()
}