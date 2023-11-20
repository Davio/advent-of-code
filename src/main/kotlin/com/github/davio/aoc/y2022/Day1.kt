package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.general.split
import com.github.davio.aoc.general.top

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/1#part2])
 */
object Day1 : Day() {

    override fun part1() =
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .max()
            .toString()

    override fun part2() =
        getInputAsSequence()
            .split(String::isBlank)
            .map { it.sumOf(String::toInt) }
            .top(3)
            .sum()
            .toString()
}
