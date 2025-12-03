package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.*

/**
 * See [Advent of Code 2023 Day 6](https://adventofcode.com/2023/day/6#part2])
 */
object Day6 : Day() {
    private val input = getInputAsLines()
    private val whitespaceRegex = Regex("\\s+")
    private val timePart = input[0].substringAfter("Time:").trim()
    private val distancePart = input[1].substringAfter("Distance:").trim()

    override fun part1(): Long {
        fun String.parseLongList() = split(whitespaceRegex).map { it.toLong() }
        val times = timePart.parseLongList()
        val distances = distancePart.parseLongList()
        return times.zip(distances).productOf { (t, d) -> getNumberOfWins(t, d) }
    }

    override fun part2(): Long {
        fun String.parseLong() = replace(" ", "").toLong()
        val time = timePart.parseLong()
        val distance = distancePart.parseLong()
        return getNumberOfWins(time, distance)
    }

    private fun getNumberOfWins(
        timeInMs: Long,
        distanceInMm: Long,
    ): Long {
        val discriminant = timeInMs.squared + 4 * (-distanceInMm - 1)
        val min = ((-timeInMs + discriminant.sqrt) / -2).roundUp()
        return (timeInMs - 2 * min) + 1
    }
}
