package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day 1](https://adventofcode.com/2023/day/1#part2])
 */
class Day1(exampleNumber: Int? = null) : Day(exampleNumber) {

    override fun part1() =
        getInputAsList()
            .map { line ->
                line.filter { c -> c.isDigit() }.let { (it.first() + it.last().toString()).toInt() }
            }
            .sum()
            .toString()

    override fun part2() =
        getInputAsList()
            .map { line -> 10 * getFirstDigit(line) + getLastDigit(line) }
            .onEach { println(it) }
            .sum()
            .toString()

    private fun getFirstDigit(line: String): Int {
        (0..2).forEach {
            if (line[it].isDigit()) return line[it].digitToInt()
        }

        val firstWordAsDigitWithIndex = digitWords.withIndex().asSequence()
            .mapNotNull { (index, word) ->
                val indexInLine = line.indexOf(word)
                if (indexInLine == -1) null else indexInLine to index
            }.minByOrNull { it.first }
        if (firstWordAsDigitWithIndex != null && firstWordAsDigitWithIndex.first < 3) {
            return firstWordAsDigitWithIndex.second
        }

        val indexOfFirstDigit = line.indexOfFirst { it.isDigit() }
        val firstDigit = if (indexOfFirstDigit != -1) line[indexOfFirstDigit].digitToInt() else null
        val firstDigitWithIndex = if (indexOfFirstDigit == -1) null else indexOfFirstDigit to firstDigit

        return listOfNotNull(firstDigitWithIndex, firstWordAsDigitWithIndex)
            .minBy { it.first }.second!!
    }

    private fun getLastDigit(line: String): Int {
        (line.lastIndex downTo line.lastIndex - 2).forEach {
            if (line[it].isDigit()) return line[it].digitToInt()
        }

        val lastWordAsDigitWithIndex = digitWords.withIndex().asSequence()
            .mapNotNull { (index, word) ->
                val indexInLine = line.lastIndexOf(word)
                if (indexInLine == -1) null else indexInLine to index
            }.maxByOrNull { it.first }
        if (lastWordAsDigitWithIndex != null && lastWordAsDigitWithIndex.first > line.lastIndex - 3) {
            return lastWordAsDigitWithIndex.second
        }

        val indexOfLastDigit = line.indexOfLast { it.isDigit() }
        val lastDigit = if (indexOfLastDigit != -1) line[indexOfLastDigit].digitToInt() else null
        val lastDigitWithIndex = if (indexOfLastDigit == -1) null else indexOfLastDigit to lastDigit

        return listOfNotNull(lastDigitWithIndex, lastWordAsDigitWithIndex)
            .maxBy { it.first }.second!!
    }

    private val digitWords = listOf(
        "zero",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    )
}
