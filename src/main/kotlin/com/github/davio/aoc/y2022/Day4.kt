package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLineSequence
import kotlin.system.measureTimeMillis

fun main() {
    println(Day4.getResultPart1())
    measureTimeMillis {
        println(Day4.getResultPart2())
    }.also { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 4](https://adventofcode.com/2022/day/4#part2])
 */
object Day4 : Day() {
    private fun parseLine(line: String) =
        line.split(",").let { lineParts ->
            lineParts[0].toIntRange() to lineParts[1].toIntRange()
        }

    fun getResultPart1(): Int {
        fun IntRange.contains(other: IntRange) = this.first in other && this.last in other

        return getInputAsLineSequence()
            .map { line -> parseLine(line) }
            .count { ranges -> ranges.first.contains(ranges.second) || ranges.second.contains(ranges.first) }
    }

    fun getResultPart2(): Int {
        fun IntRange.overlaps(other: IntRange) = this.first in other || this.last in other || other.first in this

        return getInputAsLineSequence()
            .map { line -> parseLine(line) }
            .count { ranges -> ranges.first.overlaps(ranges.second) }
    }

    private fun String.toIntRange(): IntRange =
        this.split("-").let {
            it[0].toInt()..it[1].toInt()
        }
}
