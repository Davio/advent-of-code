package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLine

/**
 * See [Advent of Code 2022 Day 6](https://adventofcode.com/2022/day/6#part2])
 */
class Day6(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private val set = hashSetOf<Char>()

    private fun getStartMarker(messageLength: Int): Long =
        (
            getInputAsLine()
                .windowedSequence(messageLength)
                .indexOfFirst { !it.hasDuplicates } + messageLength.toLong()
        )

    private val String.hasDuplicates: Boolean
        get() {
            set.clear()
            return this.any { !set.add(it) }
        }

    override fun part1() = getStartMarker(4)

    override fun part2() = getStartMarker(14)
}
