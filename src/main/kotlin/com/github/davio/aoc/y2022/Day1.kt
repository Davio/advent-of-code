package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.general.split
import com.github.davio.aoc.general.top
import kotlin.system.measureTimeMillis

fun main() {
    println(Day1.getResultPart1())
    measureTimeMillis {
        println(Day1.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/1#part2])
 */
object Day1 {

    fun getResultPart1(): Int =
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .max()

    fun getResultPart2() =
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .top(3)
            .sum()
}
