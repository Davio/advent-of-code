package com.github.davio.aoc.y2024

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2024 Day 3](https://adventofcode.com/2024/day/3)
 */
class Day3(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private val doDontMulPattern = Regex("""(do\(\))|(don't\(\))|(mul\((\d+),(\d+)\))""")

    override fun part1(): Long =
        getInputAsList().sumOf { line ->
            doDontMulPattern.findAll(line).sumOf { result ->
                multiplication(result)
            }
        }

    override fun part2(): Long {
        var enabled = true

        return getInputAsList().sumOf { line ->
            val matches = doDontMulPattern.findAll(line)

            matches.sumOf { result ->
                val doMatch = result.groups[1]
                val dontMatch = result.groups[2]
                val mulMatch = result.groups[3]

                when {
                    !enabled && doMatch != null -> enabled = true
                    enabled && dontMatch != null -> enabled = false
                    enabled && mulMatch != null -> return@sumOf multiplication(result)
                }
                0L
            }
        }
    }

    private fun multiplication(result: MatchResult): Long {
        val (x, y) =
            listOf(
                result.groups[4]!!.value,
                result.groups[5]!!.value,
            ).map { it.toLong() }

        return x * y
    }
}
