package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLine

/**
 * See [Advent of Code 2025 Day 2](https://adventofcode.com/2025/day/2)
 */
class Day2(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var ranges: List<LongRange>

    override fun part1(): Long =
        ranges
            .flatMap {
                it
            }.filter {
                it.isInvalidPart1
            }.sumOf { it }

    private val Long.isInvalidPart1: Boolean get() {
        val asString = toString()
        val firstHalf = asString.take(asString.length / 2)
        val secondHalf = asString.drop(asString.length / 2)
        return firstHalf == secondHalf
    }

    override fun part2(): Long =
        ranges
            .flatMap {
                it
            }.filter {
                it.isInvalidPart2
            }.sumOf { it }

    private val Long.isInvalidPart2: Boolean get() {
        val asString = toString()
        for (charsToTake in (1..asString.length / 2)) {
            val test = asString.take(charsToTake)
            val needsToTest = asString.length % charsToTake == 0
            if (!needsToTest) continue
            val timesToRepeat = asString.length / charsToTake
            if (test.repeat(timesToRepeat) == asString) return true
        }

        return false
    }

    override fun parseInput() {
        ranges =
            getInputAsLine().split(',').map { range ->
                val (first, last) =
                    range.split('-').map {
                        it.toLong()
                    }
                first..last
            }
    }
}
