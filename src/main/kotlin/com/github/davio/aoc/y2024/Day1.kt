package com.github.davio.aoc.y2024

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.splitToLongByWhitespace
import kotlin.math.abs

/**
 * See [Advent of Code 2024 Day 1](https://adventofcode.com/2024/day/1)
 */
class Day1(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var left: LongArray
    private lateinit var right: LongArray

    override fun part1(): Long {
        left.sort()
        right.sort()

        return left.zip(right).sumOf { (e1, e2) ->
            abs(e1 - e2)
        }
    }

    override fun part2(): Long {
        right.sort()
        val cache = mutableMapOf<Long, Long>()

        return left.sumOf { e1 ->
            cache.computeIfAbsent(e1) {
                val foundIndex = right.binarySearch(e1)
                if (foundIndex < 0) {
                    0
                } else {
                    val leftSide = (foundIndex downTo 0).takeWhile { right[it] == e1 }.count()
                    val rightSide =
                        ((foundIndex + 1)..right.lastIndex)
                            .takeWhile { it <= right.lastIndex && right[it] == e1 }
                            .count()
                    e1 * (leftSide.toLong() + rightSide.toLong())
                }
            }
        }
    }

    override fun parseInput() {
        val pairs =
            getInputAsList().map {
                val (l, r) = it.splitToLongByWhitespace()
                l.toLong() to r.toLong()
            }
        left = LongArray(pairs.size)
        right = LongArray(pairs.size)
        pairs.forEachIndexed { index, (e1, e2) ->
            left[index] = e1
            right[index] = e2
        }
    }
}
