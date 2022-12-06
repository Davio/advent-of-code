package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.getInputAsLine
import kotlin.system.measureTimeMillis

fun main() {
    println(Day6.getResultPart1())
    measureTimeMillis {
        println(Day6.getResultPart2())
    }.also { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 6](https://adventofcode.com/2022/day/6#part2])
 */
object Day6 {

    private fun getStartMarker(messageLength: Int) = getInputAsLine()
        .windowed(messageLength)
        .indexOfFirst { it.toSet().size == it.length } + messageLength

    fun getResultPart1() = getStartMarker(4)
    fun getResultPart2() = getStartMarker(14)
}
