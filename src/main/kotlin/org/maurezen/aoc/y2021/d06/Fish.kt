package org.maurezen.aoc.y2021.d06

private const val matureFishCycle = 6
private const val youngFishCycle = 8

data class Fish(var cycle: Int = matureFishCycle, var state: Int = cycle) {
    /**
     * Simulate a day passing for this specific fish
     *
     * @return a list containing this fish and spawned fish, if any
     */
    fun tick(): List<Fish> {
        val result = mutableListOf(this)
        if (state == 0) {
            result.add(Fish(youngFishCycle))
            if (youngFishCycle == cycle) {
                cycle = matureFishCycle
            }
            state = cycle
        } else {
            state--
        }
        return result
    }
}