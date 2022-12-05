package org.maurezen.aoc.utils

import java.io.File
import java.util.function.Function
import java.util.function.Predicate

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

fun <T> splitStringList(strings: List<String>, splitIf: Predicate<String>, transformer: Function<String, T>): List<List<T>> {
    val result = mutableListOf<MutableList<T>>()
    result.add(mutableListOf())
    return strings.fold(result) { listOfLists, possiblyEmptyString ->
        if (splitIf.test(possiblyEmptyString)) {
            listOfLists.add(mutableListOf())
        } else {
            listOfLists.last().add(transformer.apply(possiblyEmptyString))
        }
        listOfLists
    }
}

fun <T> splitByEmptyString(strings: List<String>, transformer: Function<String, T>): List<List<T>> {
    return splitStringList(strings, { it.trim().isEmpty() }, transformer)
}

fun splitByEmptyString(strings: List<String>): List<List<String>> {
    return splitByEmptyString(strings) { it }
}
