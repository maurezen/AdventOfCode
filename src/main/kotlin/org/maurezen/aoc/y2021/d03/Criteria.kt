package org.maurezen.aoc.y2021.d03

interface Criteria {
    fun keep(freqMap: Map<Int, Map<Char, Int>>, number: String, index: Int): Boolean
}

class OxygenCriteria : Criteria {
    override fun keep(freqMap: Map<Int, Map<Char, Int>>, number: String, index: Int): Boolean {
        return number[index] == (if (freqMap[index]!!['1']!! >= freqMap[index]!!['0']!!) '1' else '0')
    }
}

class CO2Criteria : Criteria {
    override fun keep(freqMap: Map<Int, Map<Char, Int>>, number: String, index: Int): Boolean {
        return number[index] == (if (freqMap[index]!!['0']!! <= freqMap[index]!!['1']!!) '0' else '1')
    }
}