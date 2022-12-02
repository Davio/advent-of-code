package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.general.split
import kotlin.system.measureTimeMillis

fun main() {
    Day1.getResultPart1()
    measureTimeMillis {
        Day1.getResultPart2()
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/1#part2])
 */
object Day1 {

    fun getResultPart1() {
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .max()
            .call {
                println(it)
            }
    }

    fun getResultPart2() {
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .fold(intArrayOf(0, 0, 0)) { acc, element ->
                val indexToInsert = acc.indexOfLast { element > it }
                if (indexToInsert > -1) {
                    (0 until indexToInsert).forEach { index ->
                        acc[index] = acc[index + 1]
                    }
                    acc[indexToInsert] = element
                }
                acc
            }.sum()
            .call { println(it) }
    }
}
