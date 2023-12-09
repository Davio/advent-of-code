package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day 9](https://adventofcode.com/2023/day/9#part2])
 */
object Day9 : Day() {

    private val input = getInputAsList()
    private val histories = input.map {
        it.split(' ').map { n -> n.toLong() }
    }

    override fun part1(): Long {
        return histories.sumOf { getNextValue(it) }
    }

    override fun part2(): Long {
        return histories.sumOf { getPreviousValue(it) }
    }

    private fun getNextValue(history: List<Long>): Long {
        if (history.distinct().size == 1) {
            return history.first()
        }

        return history.last() + getNextValue(history.zipWithNext { a, b -> b - a })
    }

    private fun getPreviousValue(history: List<Long>): Long {
        if (history.distinct().size == 1) {
            return history.first()
        }

        return (history.first() - getPreviousValue(history.zipWithNext { a, b -> b - a }))
    }
}
