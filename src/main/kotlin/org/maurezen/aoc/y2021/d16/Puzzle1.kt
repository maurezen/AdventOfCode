package org.maurezen.aoc.y2021.d16

import org.maurezen.aoc.Puzzle
import org.maurezen.aoc.utils.readFile
import java.util.function.BiFunction
import java.util.function.Function

open class Puzzle1 : Puzzle<Packet, Long> {

    override fun run(input: Packet): Long {
        return aggregate(input, 0) { accumulator, node -> accumulator + node.version }
    }

    override fun inputName(): String {
        return "/input16-1"
    }

    override fun input(): Packet {
        val hex = readFile(inputName()).first()
        val binary = hex.map { Integer.toBinaryString(hexCharset[it]!!).padStart(4, '0') }.joinToString("")

        println("hex = $hex")
        println("binary = $binary")
        return parseSinglePacket(binary).first
    }

    override fun dumpInput(input: Packet) {
       println(input)
    }

    private fun parseSinglePacket(
        binary: String,
        selector:  Int = 0
    ): Pair<Packet, Int> {
        var index = selector
        val version = Integer.parseInt(binary, index, index + 3, 2)
        index += 3
        val typeId = Integer.parseInt(binary, index, index + 3, 2)
        index += 3
        if (typeId == 4) {
            return parseLiteralValue(binary, index, version)
        } else {
            val lengthTypeId = Integer.parseInt(binary, index, index + 1, 2)
            index++

            var length = 0
            var amount = 0
            if (lengthTypeId == 0) {
                length = Integer.parseInt(binary, index, index + 15, 2)
                index += 15
            } else {
                amount = Integer.parseInt(binary, index, index + 11, 2)
                index += 11
            }

            val subpackets = parseSubpackets(binary, index, length, amount)
            return Pair(Operator(version, typeId, subpackets.first), subpackets.second)
        }
    }

    private fun parseSubpackets(binary: String, selector: Int, length: Int, amount: Int): Pair<List<Packet>, Int> {
        val packets = mutableListOf<Packet>()
        var index = selector
        repeat(amount) {
            val pair = parseSinglePacket(binary, index)
            packets.add(pair.first)
            index = pair.second
        }
        while (index - selector < length) {
            val pair = parseSinglePacket(binary, index)
            packets.add(pair.first)
            index = pair.second
        }
        return Pair(packets, index)
    }

    private fun parseLiteralValue(
        binary: String,
        index: Int,
        version: Int
    ): Pair<Packet, Int> {
        var next = index
        val digitParts = mutableListOf<CharSequence>()
        var tag: Char
        do {
            tag = binary[next]
            digitParts.add(binary.subSequence(next + 1, next + 5))
            next += 5
        } while ('1' == tag)
        val literalValue = LiteralValue(version, digitParts.joinToString("").toLong(2))
        return Pair(literalValue, next)
    }
}

fun <T> aggregate(tree: Packet, neutral: T, aggregator: BiFunction<T, Packet, T>): T {
    return when (tree) {
        is LiteralValue -> {
            aggregator.apply(neutral, tree)
        }
        is Operator -> {
            var result = aggregator.apply(neutral, tree)
            tree.subpackets.forEach { result = aggregate(it, result, aggregator) }
            result
        }
        else -> {
            neutral
        }
    }
}

abstract class Packet(val version: Int, val typeId: Int) {
    abstract fun evaluate(): Long
}

class LiteralValue(version: Int, private val value: Long) : Packet(version, 4) {
    override fun toString(): String {
        return "LiteralValue(version=$version typeId=$typeId value=$value)"
    }

    override fun evaluate(): Long = value
}

class Operator(version: Int, typeId: Int, val subpackets: List<Packet>): Packet(version, typeId) {

    private val type = Type.values()[typeId]

    override fun toString(): String {
        return "Operator(version=$version typeId=$typeId type=$type subpackets=$subpackets)"
    }

    override fun evaluate(): Long {
        return type.evaluator.apply(this)
    }
}

enum class Type(val evaluator: Function<Operator, Long>) {
    SUM(Function { it.subpackets.sumOf(Packet::evaluate) }),
    PRODUCT(Function { it.subpackets.map(Packet::evaluate).reduce { a, b -> a * b } }),
    MINIMUM(Function { it.subpackets.minOf(Packet::evaluate) }),
    MAXIMUM(Function { it.subpackets.maxOf(Packet::evaluate) }),
    LITERAL(Function { it.evaluate() }), //@todo this is ugly; ideally I'd like to make Literal an Operator subtype
    GREATER(Function { if (it.subpackets.first().evaluate() > it.subpackets.last().evaluate()) 1L else 0L }),
    LESS(Function { if (it.subpackets.first().evaluate() < it.subpackets.last().evaluate()) 1L else 0L }),
    EQUAL(Function { if (it.subpackets.first().evaluate() == it.subpackets.last().evaluate()) 1L else 0L })
}

val hexCharset = mapOf(
    Pair('0', 0),
    Pair('1', 1),
    Pair('2', 2),
    Pair('3', 3),
    Pair('4', 4),
    Pair('5', 5),
    Pair('6', 6),
    Pair('7', 7),
    Pair('8', 8),
    Pair('9', 9),
    Pair('A', 10),
    Pair('B', 11),
    Pair('C', 12),
    Pair('D', 13),
    Pair('E', 14),
    Pair('F', 15),
)

fun main() {
    Puzzle1().solve()
}