package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day 1](https://adventofcode.com/2023/day/1#part2])
 */
class Day1(exampleNumber: Int? = null) : Day(exampleNumber) {

    override fun part1() =
        getInputAsList().sumOf { line ->
            line.filter { c -> c.isDigit() }.let { 10 * it.first().digitToInt() + it.last().digitToInt() }
        }

    override fun part2() =
        getInputAsList().sumOf { line -> 10 * getFirstDigit(line) + getLastDigit(line) }

    private val digitWords = listOf(
        "one", "two", "three", "four", "five",
        "six", "seven", "eight", "nine"
    )
    private val digitWordsReversed = digitWords.map { it.reversed() }

    private fun getFirstDigit(line: String): Int = getNthDigit(line, digitWords)
    private fun getLastDigit(line: String): Int = getNthDigit(line.reversed(), digitWordsReversed)

    private fun getNthDigit(line: String, words: List<String>): Int {
        var buffer = ""
        var expectedChars = words.map { it.first() }.toSet()

        line.forEach { c ->
            if (c.isDigit()) return c.digitToInt()

            if (!expectedChars.contains(c)) {
                buffer = ""
                return@forEach
            }

            buffer += c
            expectedChars = words.filter { it.startsWith(buffer) }.mapNotNull { it.drop(buffer.length).firstOrNull() }.toSet()
            if (expectedChars.isEmpty()) {
                return words.indexOf(buffer) + 1
            }
        }

        return -1
    }
}
