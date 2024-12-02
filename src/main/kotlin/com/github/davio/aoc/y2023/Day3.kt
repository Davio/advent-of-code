package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.getInputAsMatrix

/**
 * See [Advent of Code 2023 Day 3](https://adventofcode.com/2023/day/3#part2])
 */
object Day3 : Day() {
    private val matrix = getInputAsMatrix()
    private val numbers: MutableList<Number> = mutableListOf()

    init {
        var start: Point? = null
        val sb = StringBuilder()

        matrix.rows.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    if (start == null) {
                        start = Point.of(x, y)
                    }
                    sb.append(c)
                } else if (sb.isNotEmpty()) {
                    val number = Number(sb.toString().toLong(), start!!, Point.of(x - 1, y))
                    numbers.add(number)
                    start = null
                    sb.clear()
                }
            }

            if (sb.isNotEmpty()) {
                val number = Number(sb.toString().toLong(), start!!, Point.of(row.lastIndex, y))
                numbers.add(number)
                start = null
                sb.clear()
            }
        }
    }

    override fun part1(): Long =
        numbers
            .filter {
                val range = (it.start..it.end)
                range.any { p ->
                    matrix.getAdjacentPoints(p).any { adjacent ->
                        val c = matrix[adjacent]
                        !c.isDigit() && c != '.'
                    }
                }
            }.sumOf { it.value }

    override fun part2(): Long =
        matrix
            .getPoints()
            .mapNotNull { p ->
                if (matrix[p] != '*') {
                    null
                } else {
                    val adjacentPartNumbers =
                        matrix
                            .getAdjacentPoints(p)
                            .flatMap { adjacentP ->
                                numbers.filter { adjacentP in (it.start..it.end) }
                            }.toSet()
                    if (adjacentPartNumbers.size == 2) {
                        adjacentPartNumbers.map { it.value }.reduce { a, b -> a * b }
                    } else {
                        null
                    }
                }
            }.sum()

    private data class Number(
        val value: Long,
        val start: Point,
        val end: Point,
    )
}
