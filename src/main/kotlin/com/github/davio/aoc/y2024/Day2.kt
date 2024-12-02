package com.github.davio.aoc.y2024

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.splitToLongByWhitespace

private typealias Levels = List<Long>

/**
 * See [Advent of Code 2024 Day 2](https://adventofcode.com/2024/day/2)
 */
class Day2(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var reports: List<Levels>

    override fun part1() = getSolution { it.isSafe }

    override fun part2() = getSolution { it.isSafeWithProblemDampener }

    fun getSolution(predicate: (Levels) -> Boolean): Int = reports.count(predicate)

    //region Part 1
    private val Levels.isSafe get() = isIncreasing || isDecreasing

    private val Levels.isIncreasing get() = isSorted(true)

    private val Levels.isDecreasing get() = isSorted(false)
    //endregion

    //region Part 2
    private val Levels.isSafeWithProblemDampener get() = isIncreasingWithProblemDampener || isDecreasingWithProblemDampener

    private val Levels.isIncreasingWithProblemDampener get() = isSortedWithDampener(true)

    private val Levels.isDecreasingWithProblemDampener get() = isSortedWithDampener(false)

    private fun Levels.isSortedWithDampener(increasing: Boolean): Boolean =
        isSorted(increasing) ||
            indices.any { i ->
                isSorted(increasing, i)
            }
    //endregion

    private fun Levels.isSorted(
        increasing: Boolean,
        skipIndex: Int = -1,
    ): Boolean {
        val levels = if (skipIndex >= 0) this.toMutableList().apply { removeAt(skipIndex) } else this
        return levels
            .zipWithNext()
            .all { (l, r) ->
                val diff = if (increasing) (r - l) else (l - r)
                diff in (1..3)
            }
    }

    override fun parseInput() {
        reports =
            getInputAsList().map {
                it.splitToLongByWhitespace()
            }
    }
}
