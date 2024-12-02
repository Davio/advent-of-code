package com.github.davio.aoc.general

abstract class Day(
    val exampleNumber: Int? = null,
) {
    open fun part1(): Number = 0

    open fun part2(): Number = 0

    open fun parseInput() {
        // Empty by default
    }

    init {
        parseInput()
    }
}
