package org.maurezen.aoc.y2021.d12

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readStuffFromFile
import java.util.function.Function

open class Puzzle1 : Puzzle<Pair<List<Cave>, Map<Cave, List<Passage>>>, Int> {

    override fun run(input: Pair<List<Cave>, Map<Cave, List<Passage>>>): Int {
        val paths = mutableSetOf<List<Passage>>()

        val start = input.first.first()
        val end = input.first.last()

        val queue = ArrayDeque(input.second[start]!!.map(::listOf))

        while (queue.isNotEmpty()) {
            val nextPath = queue.removeFirst()

            input.second[nextPath.last().to]!!.forEach { passage ->
                if (acceptable(passage, nextPath)) { //start-b + b-d unacceptable?.. nope let's investigate
                    val newPath = nextPath.toMutableList()
                    newPath.add(passage)
                    (if (passage.to == end) paths else queue).add(newPath)
                }
            }
        }

        paths.forEach {
            println("${it.map(Passage::from).joinToString(",")},$end")
        }

        return paths.size
    }

    protected open fun acceptable(
        passage: Passage,
        nextPath: List<Passage>
    ) = passage.to.big || nextPath.none { it.from == passage.to }

    override fun inputName(): String {
        return "/y2021/input12-1"
    }

    override fun input(): Pair<List<Cave>, Map<Cave, List<Passage>>> {
        val caves = mutableMapOf<String, Cave>()
        val passages = readStuffFromFile(inputName()) { string ->
            val split = string.split("-")

            split.forEach { caves.computeIfAbsent(it, Function(::Cave)) }
            //passages are bidirectional
            listOf(Passage(caves[split[0]]!!, caves[split[1]]!!), Passage(caves[split[1]]!!, caves[split[0]]!!))
        }.flatten().groupBy { it.from }.toMutableMap()
        caves.values.forEach { passages.computeIfAbsent(it) { listOf() } }
        return Pair(caves.values.sortedWith { c1, c2 ->
            if (c1.name == "start" || c2.name == "end") {
                -1
            } else if (c1.name == "end" || c2.name == "start") {
                1
            } else {
                0
            }
        }, passages)
    }

    override fun dumpInput(input: Pair<List<Cave>, Map<Cave, List<Passage>>>) {
        input.second.values.flatten().forEach(::println)
    }
}

data class Cave(val name: String, val big: Boolean = name == name.uppercase()) {
    val passages = mutableListOf<Passage>()

    override fun toString(): String = name
}

data class Passage(val from: Cave, val to: Cave) {
    init {
        from.passages.add(this)
        to.passages.add(this)
    }

    override fun toString(): String = "$from-$to"
}


fun main() {
    Puzzle1().solve()
}