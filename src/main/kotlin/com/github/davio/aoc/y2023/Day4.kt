package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day 4](https://adventofcode.com/2023/day/4#part2])
 */
object Day4 : Day() {

    private val cards = getInputAsList()
        .map { Card.parse(it) }

    override fun part1(): Int = cards.sumOf {
        it.getScore()
    }

    override fun part2(): Int {
        val totalCopies = cards.associateWith { 1 }.toMutableMap()
        return totalCopies.entries.sumOf { (card, total) ->
            card.getIdsOfCopies().forEach { id ->
                val copy = Card(id)
                totalCopies[copy] = totalCopies.getValue(copy) + total
            }
            total
        }
    }

    private data class Card(
        val id: Int
    ) {
        var winningNumbers: Set<Int> = emptySet()
        var myNumbers: Set<Int> = emptySet()

        fun getScore(): Int = if (getMatches() == 0) 0 else 1 shl (getMatches() - 1)

        fun getMatches(): Int = myNumbers.intersect(winningNumbers).size

        fun getIdsOfCopies(): List<Int> = MutableList(getMatches()) { offset ->
            id + offset + 1
        }

        companion object {
            private const val CARD_PATTERN = """Card\s+(\d+): (.+) \| (.+)"""
            private val cardRegex = Regex(CARD_PATTERN)

            fun parse(line: String): Card {
                val (idStr, winningNumbersPart, myNumbersPart) = cardRegex.matchEntire(line)!!.destructured

                fun splitNumbers(part: String): Set<Int> = part.trim().split(Regex("\\s+")).map { it.toInt() }.toSet()

                return Card(idStr.toInt())
                    .also {
                        it.winningNumbers = splitNumbers(winningNumbersPart)
                        it.myNumbers = splitNumbers(myNumbersPart)
                    }
            }
        }
    }
}
