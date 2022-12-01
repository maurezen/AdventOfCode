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
        return "/y2021/input16-1"
    }

    override fun input(): Packet {
        val hex = readFile(inputName()).first()
        val binary = hex.map { Integer.toBinaryString(hexCharset[it]!!).padStart(4, '0') }.joinToString("")

        println("hex = $hex")
        println("binary = $binary")
        return parseSinglePacket(binary.iterator())
    }

    override fun dumpInput(input: Packet) {
       println(input)
    }

    private fun parseSinglePacket(
        binary: CharIterator,
    ): Packet {
        val version = binary.take(3).toInt(2)
        val typeId = binary.take(3).toInt(2)
        return if (typeId == 4) {
            parseLiteralValue(binary, version)
        } else {
            val lengthTypeId = binary.take(1).toInt(2)

            var length = 0
            var amount = 0
            if (lengthTypeId == 0) {
                length = binary.take(15).toInt(2)
            } else {
                amount = binary.take(11).toInt(2)
            }

            val subpackets = parseSubpackets(binary, length, amount)
            Operator(
                version,
                typeId,
                subpackets,
                6 + 1 + (if (amount == 0) 15 else 11) + subpackets.sumOf(Packet::length)
            )
        }
    }

    private fun parseSubpackets(binary: CharIterator, length: Int, amount: Int): List<Packet> {
        val packets = mutableListOf<Packet>()
        repeat(amount) {
            packets.add(parseSinglePacket(binary))
        }
        while (packets.sumOf(Packet::length) < length) {
            packets.add(parseSinglePacket(binary))
        }
        return packets
    }

    private fun parseLiteralValue(
        binary: CharIterator,
        version: Int
    ): Packet {
        val digitParts = mutableListOf<CharSequence>()
        var tag: Char
        do {
            tag = binary.take(1).first()
            digitParts.add(binary.take(4).joinToString(""))
        } while ('1' == tag)
        return LiteralValue(version, digitParts.joinToString("").toLong(2), 6 + 5*digitParts.size)
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

abstract class Packet(val version: Int, val typeId: Int, val length: Int) {
    abstract fun evaluate(): Long
}

class LiteralValue(version: Int, private val value: Long, length: Int) : Packet(version, 4, length) {
    override fun toString(): String {
        return "LiteralValue(version=$version typeId=$typeId value=$value)"
    }

    override fun evaluate(): Long = value
}

class Operator(version: Int, typeId: Int, val subpackets: List<Packet>, length: Int): Packet(version, typeId, length) {

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

/**
 * Returns a list of at most n next elements of the iterator provided.
 * Throws if there is an insufficient amount of elements left.
 */
fun <T> Iterator<T>.take(n: Int): List<T> = (1..n).map { next() }

fun List<Char>.toInt(radix: Int = 10) = Integer.parseInt(joinToString(""), radix)

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