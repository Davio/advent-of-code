package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLines
import com.github.davio.aoc.general.toIntArray

/**
 * See [Advent of Code 2025 Day 3](https://adventofcode.com/2025/day/3)
 */
class Day3(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var banks: List<IntArray>

    override fun part1(): Int =
        banks.sumOf { bank ->
            val firstBatteryWithIndex =
                bank.take(bank.size - 1).withIndex().maxBy { (_, value) ->
                    value
                }

            val secondBattery =
                bank.drop(firstBatteryWithIndex.index + 1).max()

            firstBatteryWithIndex.value * 10 + secondBattery
        }

    override fun part2(): Long =
        banks.sumOf {
            getLargestJoltagePart2(it)
        }

    private fun getLargestJoltagePart2(bank: IntArray): Long {
        var index = -1
        var total = 0L

        (12 downTo 1).forEach { digitsRemaining ->
            val nextHighestBattery = getHighestRemainingBattery(bank, index + 1, digitsRemaining)
            index = nextHighestBattery.index
            total = 10L * total + nextHighestBattery.value
        }

        return total
    }

    private fun getHighestRemainingBattery(
        batteries: IntArray,
        currentIndex: Int,
        digitsRemaining: Int,
    ): IndexedValue<Int> {
        val maxIndex = batteries.lastIndex - digitsRemaining + 1
        return batteries
            .withIndex()
            .toList()
            .subList(currentIndex, maxIndex + 1)
            .maxBy { it.value }
    }

    override fun parseInput() {
        banks =
            getInputAsLines().map {
                it.toIntArray()
            }
    }
}
