package com.github.davio.aoc.general

abstract class Day(
    val exampleNumber: Int? = null,
) {
    open fun part1(): Any = ""

    open fun part2(): Any = ""

    open fun parseInput() {
        // Empty by default
    }

    init {
        parseInput()
    }
}

typealias Puzzle = Day
