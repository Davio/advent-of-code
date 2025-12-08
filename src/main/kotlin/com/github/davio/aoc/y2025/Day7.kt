package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Matrix
import com.github.davio.aoc.general.getInputAsMatrix

/**
 * See [Advent of Code 2025 Day 7](https://adventofcode.com/2025/day/7)
 */
class Day7(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var diagram: Matrix<Char>

    override fun part1(): Long {
        val start = diagram.getPointsWithValues().first { it.value == 'S' }
        var splits = 0L
        (start.point.down().y..diagram.lastYIndex).forEach { y -> 
            diagram.getRow(y).forEachIndexed { x, char ->
                val above = diagram[x, y - 1]
                if (above == 'S' || above == '|') {
                    if (char == '^') {
                        diagram[x - 1, y] = '|'
                        diagram[x + 1, y] = '|'
                        splits++
                    } else {
                        diagram[x, y] = '|'
                    }
                }
            }
        }
        
        return splits
    }

    override fun part2(): Long {
        val start = diagram.getPointsWithValues().first { it.value == 'S' }
        var splits = 0L
        (start.point.down().y..diagram.lastYIndex).forEach { y ->
            diagram.getRow(y).forEachIndexed { x, char ->
                val above = diagram[x, y - 1]
                if (above == 'S' || above == '|') {
                    if (char == '^') {
                        diagram[x - 1, y] = '|'
                        diagram[x + 1, y] = '|'
                        splits++
                    } else {
                        diagram[x, y] = '|'
                    }
                }
            }
        }

        return splits
    }

    override fun parseInput() {
        diagram =  getInputAsMatrix()
    }
}
