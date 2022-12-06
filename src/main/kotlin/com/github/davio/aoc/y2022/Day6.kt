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

    private val set = hashSetOf<Char>()

    private fun getStartMarker(messageLength: Int) = getInputAsLine()
        .windowedSequence(messageLength)
        .indexOfFirst { !it.hasDuplicates } + messageLength

    private val String.hasDuplicates: Boolean get() {
        set.clear()
        return this.any { !set.add(it) }
    }

    fun getResultPart1() = getStartMarker(4)
    fun getResultPart2() = getStartMarker(14)
}
