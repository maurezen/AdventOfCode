package org.maurezen.aoc.y2022.d07

open class Puzzle2: Puzzle1() {

    private val DISK_TOTAL = 70000000
    private val DISK_REQUIRED = 30000000

    override fun run(input: List<CommandAndOutput>): Int {
        val root = File("/", 0, null, mutableMapOf())
        buildDirectoryTree(root, input)

        calculateSizes(root)

        val spaceNeeded = DISK_REQUIRED - (DISK_TOTAL - root.size)

        return collectDirectories(root).filter { it.size > spaceNeeded }.minByOrNull { it.size }!!.size
    }

    private fun collectDirectories(root: File): List<File> {
        val result = mutableListOf<File>()
        depthFirstWalk(root) {
            if (it.children.isNotEmpty()) {
                result.add(it)
            }
        }
        return result
    }

    override fun inputName(): String {
        return "/y2022/input07-1"
//        return "/y2022/input07-1-test"
    }
}

fun main() {
    Puzzle2().solve()
}