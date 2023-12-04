package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList
import kotlin.math.pow

/**
 * See [Advent of Code 2023 Day 4](https://adventofcode.com/2023/day/4#part2])
 */
object Day4 : Day() {

    private val cards = getInputAsList()
        .map { Card.parse(it) }

    override fun part1(): Int {
        return cards.sumOf {
            it.getScore()
        }
    }

    override fun part2(): Int {
        val totalCopies = cards.associateWith { 1 }.toMutableMap()
        totalCopies.forEach { (card, total) ->
            card.getIdsOfCopies().forEach { id ->
                val copy = Card(id)
                totalCopies[copy] = totalCopies.getValue(copy) + total
            }
        }
        return totalCopies.values.sum()
    }

    private data class Card(
        val id: Int
    ) {
        var winningNumbers: Set<Int> = emptySet()
        var myNumbers: Set<Int> = emptySet()

        fun getScore(): Int = 2.0.pow(getMatches() - 1).toInt()

        fun getMatches(): Int = myNumbers.intersect(winningNumbers).size

        fun getIdsOfCopies(): List<Int> = (1..getMatches()).map { offset ->
            id + offset
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
