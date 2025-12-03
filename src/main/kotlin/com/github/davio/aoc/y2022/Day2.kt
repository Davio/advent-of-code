package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsLineSequence
import com.github.davio.aoc.y2022.Day2.Outcome.*
import com.github.davio.aoc.y2022.Day2.RockPaperScissors.*
import kotlin.system.measureTimeMillis

fun main() {
    Day2.getResultPart1()
    measureTimeMillis {
        Day2.getResultPart2()
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 2](https://adventofcode.com/2022/day/2#part2])
 */
object Day2 : Day() {
    enum class Outcome(
        val points: Int,
    ) {
        WIN(6),
        DRAW(3),
        LOSE(0),
    }

    enum class RockPaperScissors(
        val points: Int,
    ) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
    }

    private val opponentCodeMap =
        mapOf(
            'A' to ROCK,
            'B' to PAPER,
            'C' to SCISSORS,
        )

    private val rspStructure = arrayOf(SCISSORS, ROCK, PAPER)

    private fun RockPaperScissors.getOutcomeAgainstOpponent(opponentChoice: RockPaperScissors): Outcome {
        if (this == opponentChoice) {
            return DRAW
        }

        val choiceToWin = rspStructure[(rspStructure.indexOf(opponentChoice) + 1) % rspStructure.size]

        return if (this == choiceToWin) {
            WIN
        } else {
            LOSE
        }
    }

    fun getResultPart1() {
        val yourCodeMap =
            mapOf(
                'X' to ROCK,
                'Y' to PAPER,
                'Z' to SCISSORS,
            )

        getInputAsLineSequence()
            .map {
                val opponentChoice = opponentCodeMap[(it.first())]!!
                val yourChoice = yourCodeMap[it[2]]!!
                yourChoice.points + yourChoice.getOutcomeAgainstOpponent(opponentChoice).points
            }.sum()
            .call { println(it) }
    }

    fun getResultPart2() {
        val desiredOutcomeMap =
            mapOf(
                'X' to LOSE,
                'Y' to DRAW,
                'Z' to WIN,
            )

        getInputAsLineSequence()
            .map {
                val opponentChoice = opponentCodeMap[(it.first())]!!
                val desiredOutcome = desiredOutcomeMap[it[2]]!!

                desiredOutcome.points +
                    when (desiredOutcome) {
                        DRAW -> opponentChoice.points
                        WIN -> rspStructure[(rspStructure.indexOf(opponentChoice) + 1) % rspStructure.size].points
                        LOSE -> {
                            val losingIndex =
                                (rspStructure.indexOf(opponentChoice) - 1).let { index ->
                                    if (index < 0) index + rspStructure.size else index
                                }
                            rspStructure[losingIndex].points
                        }
                    }
            }.sum()
            .call { println(it) }
    }
}
