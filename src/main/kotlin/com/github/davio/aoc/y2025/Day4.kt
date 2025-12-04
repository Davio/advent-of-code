package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Matrix
import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.getInputAsMatrix

/**
 * See [Advent of Code 2025 Day 4](https://adventofcode.com/2025/day/4)
 */
class Day4(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var grid: Matrix<Boolean>

    override fun part1(): Int =
        grid
            .getPointsWithValues()
            .count { it.value && rollCanBeRemoved(it.point) }

    private fun rollCanBeRemoved(point: Point): Boolean =
        grid
            .getAdjacentValues(point)
            .filter { it }
            .count() < 4

    override fun part2(): Int {
        var totalRemoved = 0

        do {
            val rollsToRemove =
                grid
                    .getPointsWithValues()
                    .filter { it.value && rollCanBeRemoved(it.point) }
                    .toList()

            totalRemoved += rollsToRemove.size

            rollsToRemove.forEach {
                grid[it.point] = false
            }
        } while (rollsToRemove.isNotEmpty())

        return totalRemoved
    }

    override fun parseInput() {
        grid =
            getInputAsMatrix {
                when (it) {
                    '.' -> false
                    else -> true
                }
            }
    }
}
