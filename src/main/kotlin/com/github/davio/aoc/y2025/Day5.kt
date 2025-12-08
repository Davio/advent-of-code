package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsChunks
import com.github.davio.aoc.general.size
import com.github.davio.aoc.general.toULongRange
import kotlin.math.max
import kotlin.math.min

/**
 * See [Advent of Code 2025 Day 5](https://adventofcode.com/2025/day/5)
 */
class Day5(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var idRanges: List<ULongRange>
    private lateinit var ids: List<ULong>

    override fun part1(): Int =
        ids.count {
            idRanges.any { range ->
                it in range
            }
        }

    override fun part2(): ULong {
        val allDisjointRanges = mutableListOf(idRanges[0])

        idRanges.drop(1).forEach { currentRange ->
            var currentDisjointRanges = listOf(currentRange)
            allDisjointRanges.forEach { previousRange ->
                currentDisjointRanges =
                    currentDisjointRanges.flatMap { currentDisjointRange ->
                        getDisjointRanges(previousRange, currentDisjointRange)
                    }
            }

            allDisjointRanges.addAll(currentDisjointRanges)
        }

        return allDisjointRanges.sumOf { it.size }
    }

    private fun getIntersection(
        range1: ULongRange,
        range2: ULongRange,
    ): ULongRange {
        if (range2.first > range1.last || range2.last < range1.first) return ULongRange.EMPTY

        return max(range1.first, range2.first)..min(range1.last, range2.last)
    }

    fun getDisjointRanges(
        firstRange: ULongRange,
        secondRange: ULongRange,
    ): List<ULongRange> {
        if (secondRange == ULongRange.EMPTY) return emptyList()

        val intersection = getIntersection(firstRange, secondRange)
        if (intersection.isEmpty()) return listOf(secondRange)

        val leftSide = (secondRange.first until firstRange.first).takeIf { it.size > 0uL }
        val rightSide = ((firstRange.last + 1uL)..secondRange.last).takeIf { it.size > 0uL }
        return listOfNotNull(leftSide, rightSide)
    }

    override fun parseInput() {
        getInputAsChunks().let { (idRangesChunk, idsChunk) ->

            idRanges =
                idRangesChunk.lines().map {
                    it.toULongRange()
                }

            ids =
                idsChunk.lines().map {
                    it.toULong()
                }
        }
    }
}
