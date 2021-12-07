package org.maurezen.aoc.utils

import java.io.File
import java.util.function.Function

class Locator {}

/**
 * Reads file from resources
 */
fun readFile(name: String): List<String> {
    return readStuffFromFile(name) { it }
}

fun readNumbersFromFile(name: String): List<Int> {
    return readStuffFromFile(name) { Integer.parseInt(it) }
}

fun readCSVNumbersFromFile(name: String): List<Int> {
    return readStuffFromFile(name) { it.split(",").map(Integer::parseInt) }[0]
}

fun <T> readStuffFromFile(name: String, transformer: Function<String, T>): List<T> {
    val path = Locator::class.java.getResource(name).path
    val list = mutableListOf<T>()
    File(path).useLines { sequence -> sequence.forEach { list.add(transformer.apply(it)) } }
    return list
}
