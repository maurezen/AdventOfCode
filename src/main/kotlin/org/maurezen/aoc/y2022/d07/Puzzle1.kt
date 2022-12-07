package org.maurezen.aoc.y2022.d07

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.utils.splitStringList
import java.util.function.Consumer

open class Puzzle1: ListPuzzle<CommandAndOutput, Int> {
    override fun run(input: List<CommandAndOutput>): Int {
        val root = File("/", 0, null, mutableMapOf())
        buildDirectoryTree(root, input)

        calculateSizes(root)

        dumpTree(root)
        println("Size of the whole structure is ${root.size}")

        return calculateSizesUnder(root, 100000)
    }

    private fun calculateSizesUnder(root: File, threshold: Int): Int {
        var filteredSize = 0

        depthFirstWalk(root) {
            if (it.children.isNotEmpty() && it.size < threshold) {
                filteredSize += it.size
            }
        }
        return filteredSize
    }

    protected fun calculateSizes(root: File) {
        depthFirstWalk(root) {
            if (it.children.isNotEmpty()) {
                it.size = it.children.values.sumOf(File::size)
            }
        }
    }

    protected fun dumpTree(root: File) {
        depthFirstWalk(root) {
            println("File/dir ${it.name}, child of ${it.parent}, size ${it.size}")
        }
    }

    protected fun depthFirstWalk(start: File, visitor: Consumer<File>) {
        start.children.forEach { depthFirstWalk(it.value, visitor) }
        visitor.accept(start)
    }

    protected fun buildDirectoryTree(
        root: File,
        input: List<CommandAndOutput>
    ) {
        var current = root
        input.forEach { command ->
            if (command.command.startsWith("$ cd ")) {
                val name = command.command.substring(5)
                if (name == "/") {
                    current = root
                } else if (name == "..") {
                    if (current != root) {
                        current = current.parent!!
                    } else {
                        println("wtf this is a go up command and we're in root already: ${command.command}")
                    }
                } else {
                    //we expect that cd only happens after an ls
                    current = current.children[name]!!
//                    val child = File(name, 0, current, mutableMapOf())
//                    current.children[child.name] = child
//                    current = child
                }
            } else {
                command.output.map { it.split(" ") }.forEach { out ->
                    if (out[0].startsWith("dir")) {
                        current.children[out[1]] = File(out[1], 0, current, mutableMapOf())
                    } else {
                        current.children[out[1]] = File(out[1], Integer.parseInt(out[0]), current, mutableMapOf())
                    }
                }
            }
        }
    }

    override fun inputName(): String {
        return "/y2022/input07-1"
//        return "/y2022/input07-1-test"
    }

    override fun input(): List<CommandAndOutput> {
        val commandsAndOutputs = splitStringList(readFile(inputName()), true, { it.startsWith("$") }, { it })
        return commandsAndOutputs
            .filterNot { it.isEmpty() }
            .map { CommandAndOutput(it.first(), it.subList(1, it.size)) }
    }
}

class CommandAndOutput(val command: String, val output: List<String>) {
}

//we'll index by name just in case because why not. we'll also consider everything that doesn't have descendants a directory.
class File(val name: String, var size: Int, val parent: File?, val children: MutableMap<String, File>) {

}


fun main() {
    Puzzle1().solve()
}