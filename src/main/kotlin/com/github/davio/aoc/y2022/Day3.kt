package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsSequence
import kotlin.system.measureTimeMillis

fun main() {
    println(Day3.getResultPart1())
    measureTimeMillis {
        println(Day3.getResultPart2())
    }.also { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 3](https://adventofcode.com/2022/day/3#part2])
 */
object Day3 : Day() {

    fun getResultPart1() = getInputAsSequence()
        .map { line -> line.chunked(line.length / 2) }
        .map { compartments ->
            compartments[0].first { c -> compartments[1].contains(c) }
        }.sumOf { c -> getPriority(c) }

    fun getResultPart2() = getInputAsSequence()
        .chunked(3)
        .map { rucksacks ->
            rucksacks[0].first { c -> rucksacks[1].contains(c) && rucksacks[2].contains(c) }
        }.sumOf { c -> getPriority(c) }

    private fun getPriority(c: Char): Int = (c.lowercaseChar().code - '`'.code) + if (c.isUpperCase()) 26 else 0
}
