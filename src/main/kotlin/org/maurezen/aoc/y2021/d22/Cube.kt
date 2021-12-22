package org.maurezen.aoc.y2021.d22

import org.maurezen.aoc.y2021.d18.X
import org.maurezen.aoc.y2021.d18.x

//assumes this is lit
//sure as fuck hopes that an intersection is a single cube
//returns it as lit
//returns empty lsit if the intersection is empty
fun Cube.intersect(another: Cube): List<Cube> {
    val intersectionX = second.intersect(another.second)
    val intersectionY = third.intersect(another.third)
    val intersectionZ = fourth.intersect(another.fourth)
    return if (intersectionX.isEmpty() || intersectionY.isEmpty() || intersectionZ.isEmpty()) {
        emptyList()
    } else {
        listOf(Cube(first, intersectionX, intersectionY, intersectionZ))
    }
}

//assumes this is lit and another is not
//returns a set of (hopefully less than) 26 cubes that are this minus that
//all of them are going to be lit
fun Cube.minus(another: Cube): List<Cube> {
    //gotta be presenting this cube as a set of disjunct adjacent cubes, one of them being the intersection
    //it would be easy to drop one extra thing
    //a set of disjunct adjacent cubes is basically a cartesian product of a list of intervals for each coordinate
    //and we can do that one, right
    val intersection = intersect(another).firstOrNull()
    return if (intersection == null) {
        listOf(this)
    } else {
        split(intersection).minus(intersection)
    }
}

//split this cube into a set of disjunct cubes with another being one of them
//assumes this is lit and returns a set of lit cubes
//assumes another is fully within this
private fun Cube.split(another: Cube): List<Cube> {
    return (second.split(another.second) x third.split(another.third) X fourth.split(another.fourth))
        .map { Cube(first, it.first, it.second, it.third) }
}