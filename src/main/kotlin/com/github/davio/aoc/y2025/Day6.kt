package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Matrix
import com.github.davio.aoc.general.getInputAsLines

/**
 * See [Advent of Code 2025 Day 6](https://adventofcode.com/2025/day/6)
 */
class Day6(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var problems: Matrix<String>

    override fun part1(): Long =
        problems.columns.sumOf { column ->
            val operator = getOperator(column.last().trim()[0])
            column
                .dropLast(1)
                .map { n ->
                    n.trim().toLong()
                }.reduce(operator)
        }

    override fun part2(): Long =
        problems.columns.reversed().sumOf { column ->
            val operator = getOperator(column.last().trim()[0])
            val numberParts = column.dropLast(1)
            val maxIndex = numberParts.maxOf { it.length } - 1
            (maxIndex downTo 0)
                .map { index ->
                    numberParts
                        .map { part -> if (index > part.lastIndex) '0' else part[index] }
                        .also {
                            println(it)
                        }.toCharArray()
                        .concatToString()
                        .trim()
                        .toLong()
                }.reduce(operator)
        }

    private fun getOperator(opChar: Char): (Long, Long) -> Long =
        when (opChar) {
            '*' -> Long::times
            '+' -> Long::plus
            else -> throw IllegalArgumentException()
        }

    override fun parseInput() {
        val lines = getInputAsLines()
        val splitIndices =
            (0..<lines[0].length).filter { index ->
                lines.all { line -> index <= line.lastIndex && line[index] == ' ' }
            }

        problems =
            Matrix(
                lines.map { line ->
                    var startIndex = 0
                    val tokens =
                        splitIndices
                            .map { nextSplitIndex ->
                                val token = line.substring(startIndex, nextSplitIndex)
                                startIndex = nextSplitIndex + 1
                                token
                            }.toMutableList()
                    tokens.add(line.substring(startIndex))
                    tokens
                },
            )
    }
}
