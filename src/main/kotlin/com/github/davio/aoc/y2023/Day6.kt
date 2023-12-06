package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.*

/**
 * See [Advent of Code 2023 Day 6](https://adventofcode.com/2023/day/6#part2])
 */
object Day6 : Day() {

    private val input = getInputAsList()
    private val whitespaceRegex = Regex("\\s+")
    private val timePart = input[0].substringAfter("Time:").trim()
    private val distancePart = input[1].substringAfter("Distance:").trim()

    override fun part1(): Long {
        val times = timePart.split(whitespaceRegex).map { it.toLong() }
        val distances = distancePart.split(whitespaceRegex).map { it.toLong() }
        return times.foldIndexed(1) { index, acc, time -> acc * getNumberOfWins(time, distances[index]) }
    }

    override fun part2(): Long {
        val time = timePart.replace(" ", "").toLong()
        val distance = distancePart.replace(" ", "").toLong()
        return getNumberOfWins(time, distance)
    }

    private fun getNumberOfWins(timeInMs: Long, distanceInMm: Long): Long {
        val discriminant = timeInMs.squared + 4 * (-distanceInMm - 1)
        val min = ((-timeInMs + discriminant.sqrt) / -2).roundUp()
        return (timeInMs - 2 * min) + 1
    }
}
