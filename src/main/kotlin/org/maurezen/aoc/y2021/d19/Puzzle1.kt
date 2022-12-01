package org.maurezen.aoc.y2021.d19

import org.maurezen.aoc.ListPuzzle
import org.maurezen.aoc.utils.readFile
import org.maurezen.aoc.y2021.d18.x
import space.kscience.kmath.linear.LinearSpace
import space.kscience.kmath.linear.Matrix
import space.kscience.kmath.linear.Point
import space.kscience.kmath.linear.matrix
import space.kscience.kmath.nd.StructureND.Companion.contentEquals
import space.kscience.kmath.operations.IntRing
import space.kscience.kmath.structures.Buffer

open class Puzzle1 : ListPuzzle<ScannerReport, Int> {

    override fun run(input: List<ScannerReport>): Int {

        val list = input.toMutableList()

        while (list.size > 1) {
            run outer@{
                (list.indices x list.indices).filterNot { it.first == it.second }.forEach {
                    val intersect = list[it.first].intersect(list[it.second])
                    if (intersect.first.size >= 12) {
                        list[it.first] = list[it.first].union(list[it.second], intersect.second, intersect.third, intersect.fourth).first
                        list.removeAt(it.second)
                        // gonna burn in hell for this, but at this point into day 19 idgaf
                        return@outer
                    }
                }
            }
        }
        
        return list.first().measurements.size
    }

    override fun inputName(): String {
        return "/y2021/input19-1"
    }

    override fun input(): List<ScannerReport> {
        val list = mutableListOf<ScannerReport>()
        val lines = readFile(inputName())
        var points = mutableSetOf<Point3D>()
        lines.forEach { line ->
            when {
                line.startsWith("---") -> {}
                line.isEmpty() -> {
                    list.add(ScannerReport(points))
                    points = mutableSetOf()
                }
                else -> {
                    val ints = line.split(",").map(Integer::parseInt)
                    points.add(natural.buildVector(3) { ints[it] })
                }
            }
        }
        list.add(ScannerReport(points))
        return list
    }

    override fun dumpInput(input: List<ScannerReport>) {
        input.forEachIndexed{ index, report ->
            println("--- scanner $index ---")

            report.measurements.forEach {
                println("${it[0]},${it[1]},${it[2]}")
            }

            println()
        }
    }

    private fun dumpRotations() {
        rotations.forEachIndexed { index, matrix ->
            println("Rotation $index")
            println(matrix)
        }
    }
}

class ScannerReport(val measurements: Set<Point3D>) {

    // returns an intersection of at least 12 points or an empty list
    fun intersect(another: ScannerReport): Quadruple<List<Point3D>, Matrix<Int>, Int, Int> {
        //translate this measurements to some
        measurements.forEachIndexed { index, anchor ->
            val anchored = measurements.map { with(natural) { it - anchor } }

            //for each possible change of orientation of another
            rotations.forEach { rotation ->
                val anotherRotated = rotate(another.measurements, rotation)
                //for each possible point in another set an anchor
                anotherRotated.forEachIndexed { anotherIndex, anotherAnchor ->
                    //translate another measurements to that anchor
                    val anotherAnchored = anotherRotated.map { with(natural) { it - anotherAnchor } }

                    val intersect = anchored.intersect(anotherAnchored, pointparator)
                    if (intersect.size >= 12) {
                        //translate the intersection back to this coords (we're in this coords, so no rotation back
                        val native = intersect.map { with(natural) { it + anchor } }
                        return Quadruple(native, rotation, index, anotherIndex)
                    }
                }
            }
        }

        return Quadruple(listOf(), neutral, -42, -42)
    }

    // returns a union of points in this scanner's coordinates
    fun union(another: ScannerReport, rotation: Matrix<Int>, anchorIndex: Int, anotherAnchorIndex: Int): Pair<ScannerReport, Point<Int>> {
        val union = mutableListOf<Point3D>()
        val anchor = measurements.toList()[anchorIndex]
        union.addAll(measurements.map { with(natural) { it - anchor } })

        val anotherRotated = rotate(another.measurements, rotation)
        val anotherAnchor = anotherRotated[anotherAnchorIndex]
        union.addAll(anotherRotated.map { with(natural) { it - anotherAnchor } })

        val anotherCoord = with(natural) { anchor - anotherAnchor }

        return Pair(ScannerReport(union.deduplicate(pointparator).map { with(natural) { it + anchor } }.toSet()), anotherCoord)
    }
}

fun rotate(points: Collection<Point3D> , rotation: Matrix<Int>): List<Point3D> {
    return points.map { with(natural) { return@map rotation dot it } }
}

typealias Point3D = Point<Int>

data class Quadruple<T1, T2, T3, T4>(val first: T1, val second: T2, val third: T3, val fourth: T4)

val natural = LinearSpace.auto(IntRing)

val neutral = natural.matrix(3, 3)(
    1,0,0,
    0,1,0,
    0,0,1)

val rotationX = natural.matrix(3, 3)(
    1,0,0,
    0,0,-1,
    0,1,0)

val rotationY = natural.matrix(3, 3)(
    0,0,-1,
    0,1,0,
    1,0,0)

val rotationZ = natural.matrix(3, 3)(
    0,-1,0,
    1,0,0,
    0,0,1)

val matrixparator: (o1: Matrix<Int>, o2: Matrix<Int>) -> Int = { a, b -> if (contentEquals(a, b)) 0 else -42 }
val pointparator: (o1: Point<Int>, o2: Point<Int>) -> Int = { a, b -> if (Buffer.contentEquals(a, b)) 0 else -42 }

var rotations = (0..3 x 0..3 x 0..3).flatten().map {
    var rotation = neutral
    repeat(it.first) { rotation = with(natural) { rotation dot rotationX }}
    repeat(it.second) { rotation = with(natural) { rotation dot rotationY } }
    repeat(it.third) { rotation = with(natural) { rotation dot rotationZ } }
    rotation
}.toList().deduplicate(matrixparator)

private fun <E> Collection<E>.deduplicate(comparator: Comparator<E>): List<E> {
    val seen = mutableListOf<E>()
    forEach { source ->
        if (seen.none { comparator.compare(it, source) == 0 }) {
            seen.add(source)
        }
    }
    return seen
}

private fun <E> Collection<E>.contains(what: E, comparator: Comparator<E>): Boolean {
    return any { comparator.compare(it, what) == 0 }
}

private fun <E> Collection<E>.intersect(another: Collection<E>, comparator: Comparator<E>): List<E> {
    return filter { another.contains(it, comparator) }
}


fun<U, V, W> Iterable<Pair<Pair<U, V>, W>>.flatten(): List<Triple<U, V, W>> {
    return map { Triple(it.first.first, it.first.second, it.second) }
}

fun main() {
    Puzzle1().solve()
}
