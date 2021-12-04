package org.maurezen.aoc.y2021.d03

open class Puzzle2: Puzzle1() {
    /**
     * Assuming equal string lengths
     * Assuming non-empty input
     */
    override fun run(input: List<String>): Int {
        val oxygen = Integer.parseInt(filterEveryDigit(input, OxygenCriteria()).first(), 2)
        val co2 = Integer.parseInt(filterEveryDigit(input, CO2Criteria()).first(), 2)

        return oxygen*co2
    }

    private fun filterEveryDigit(
        input: List<String>,
        oxygenChecker: Criteria
    ): List<String> {
        var oxygenList = input.toList()
        var index = 0
        while (oxygenList.size > 1 && index < input.first().length) {
            val freqMap = calculateFrequencies(oxygenList)

            oxygenList = oxygenList.filter { oxygenChecker.keep(freqMap, it, index) }

            index++
        }

        return oxygenList
    }

    private fun calculateFrequencies(input: List<String>): MutableMap<Int, MutableMap<Char, Int>> {
        val freqMap = mutableMapOf<Int, MutableMap<Char, Int>>()

        input.first().forEachIndexed { index, _ -> freqMap[index] = calculateFrequenciesForDigit(input, index) }
        return freqMap
    }

    private fun calculateFrequenciesForDigit(
        input: List<String>,
        index: Int
    ): MutableMap<Char, Int> {
        val indexMap = mutableMapOf<Char, Int>()

        input.forEachIndexed() { _, c ->
            indexMap.merge(c[index], 1) { t, u -> t + u }
        }

        return indexMap
    }

}

fun main() {
    Puzzle2().solve()
}