package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.getInputAsSequence
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
object Day4 {

    fun getResultPart1(): Int {
        fun IntRange.contains(other: IntRange) = this.first <= other.first && this.last >= other.last

        return getInputAsSequence()
            .map { line -> line.split(",") }
            .map { lineParts -> lineParts[0].toIntRange() to lineParts[1].toIntRange() }
            .count { ranges -> ranges.first.contains(ranges.second) || ranges.second.contains(ranges.first) }
    }

    fun getResultPart2(): Int {
        fun IntRange.overlaps(other: IntRange) = this.any { other.contains(it) }

        return getInputAsSequence()
            .map { line -> line.split(",") }
            .map { lineParts -> lineParts[0].toIntRange() to lineParts[1].toIntRange() }
            .count { ranges -> ranges.first.overlaps(ranges.second) }
    }

    private fun String.toIntRange(): IntRange {
        val parts =this.split("-")
        return parts[0].toInt()..parts[1].toInt()
    }
}
