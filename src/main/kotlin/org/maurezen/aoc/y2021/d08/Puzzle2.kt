package org.maurezen.aoc.y2021.d08

class Puzzle2: Puzzle1() {

    override fun run(input: List<Pair<List<String>, List<String>>>): Int {
        return input.sumOf { decode(it.second, decipher(it.first)) }
    }

    private fun decode(reading: List<String>, mappings: Map<Char, Char>): Int {
        var number = 0
        reading.forEach { s ->
            val digit = decode(s, mappings)   
            number *= 10
            number += digit
        }
        return number
    }

    //should probably had done bitmaps instead of char sets, but w/e
    private val digitMap = mapOf(
        Pair(setOf('a', 'b', 'c',      'e', 'f', 'g'), 0),
        Pair(setOf(          'c',           'f'     ), 1),
        Pair(setOf('a',      'c', 'd', 'e',      'g'), 2),
        Pair(setOf('a',      'c', 'd',      'f', 'g'), 3),
        Pair(setOf(     'b', 'c', 'd',      'f'     ), 4),
        Pair(setOf('a', 'b',      'd',      'f', 'g'), 5),
        Pair(setOf('a', 'b',      'd', 'e', 'f', 'g'), 6),
        Pair(setOf('a',      'c',           'f'     ), 7),
        Pair(setOf('a', 'b', 'c', 'd', 'e', 'f', 'g'), 8),
        Pair(setOf('a', 'b', 'c', 'd',      'f', 'g'), 9)
    )
    
    private fun decode(symbol: String, mappings: Map<Char, Char>): Int {
        val decoded = symbol.toCharArray().map { mappings[it]!! }.toSet()
        return digitMap[decoded.toSortedSet()]!!
    }

    //this is ineffective as fuck, but in the grand scheme of things is O(1)
    private fun decipher(digits: List<String>): Map<Char, Char> {
        val charsets = digits.map { it.toCharArray().toSet() }.toMutableList()

        val sixes = charsets.filter { it.size == 6}
        val fives = charsets.filter { it.size == 5}

        val one = charsets.filter {it.size == 2 }[0]
        val four = charsets.filter {it.size == 4}[0]
        val seven = charsets.filter {it.size == 3}[0]
        val eight = charsets.filter {it.size == 7}[0]

        val a = seven.minus(one)
        val bd = four.minus(one)

        //abfg
        val s069 = sixes.reduce(Set<Char>::intersect)
        val b = bd.intersect(s069)
        val f = one.intersect(s069)
        val c = one.minus(f)
        val g = s069.minus(a).minus(b).minus(f)

        //adg
        val s235 = fives.reduce(Set<Char>::intersect)
        val d = s235.minus(a).minus(g)

        val e = eight.asSequence().minus(a).minus(b).minus(c).minus(d).minus(f).minus(g).toList()

        return mapOf(
            Pair(a.first(), 'a'),
            Pair(b.first(), 'b'),
            Pair(c.first(), 'c'),
            Pair(d.first(), 'd'),
            Pair(e.first(), 'e'),
            Pair(f.first(), 'f'),
            Pair(g.first(), 'g')
        )
    }
}

fun main() {
    Puzzle2().solve()
}