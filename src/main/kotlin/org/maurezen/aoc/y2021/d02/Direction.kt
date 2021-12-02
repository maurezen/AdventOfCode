package org.maurezen.aoc.y2021.d02

/**
 * x: horisontal position, forward is positive
 * y: vertical position, depth is positive
 */
enum class Direction(val signX: Int, val signY: Int) {
    forward(1,0),
    down(0, 1),
    up(0, -1);
}