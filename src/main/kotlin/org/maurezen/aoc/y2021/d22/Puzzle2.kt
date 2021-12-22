package org.maurezen.aoc.y2021.d22

class Puzzle2: Puzzle1() {

    override fun run(input: List<Cube>): Long {
        return countPixelsCubeSplitter(input)
    }

    private fun countPixelsCubeSplitter(input: List<Cube>): Long {
        var reduction = mutableListOf<Cube>()

        var counter = 0L
        input.forEachIndexed { index, cube ->
            println("processing cube ${index+1} of ${input.size}; need to look at ${reduction.size} cubes")
            if (reduction.isNotEmpty()) {
                reduction = reduction.map { it.minus(cube) }.flatten().toMutableList()
            }
            if (cube.first) {
                reduction.add(cube)
            }
            counter = reduction.volume()
            println("lit pixels this far: ${reduction.volume() }")
        }

        return counter
    }

    private fun List<Cube>.volume() = sumOf { it.volume() }

    private fun Cube.volume() = second.size().toLong() * third.size() * fourth.size()

}

fun main() {
    Puzzle2().solve()
}