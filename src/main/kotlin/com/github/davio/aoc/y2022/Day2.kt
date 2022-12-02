package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.y2022.Day2.RockPaperScissors.*
import kotlin.system.measureTimeMillis

fun main() {
    Day2.getResultPart1()
    measureTimeMillis {
        Day2.getResultPart2()
    }.call { println("Took $it ms") }
}

object Day2 {

    /*
    */
    enum class RockPaperScissors(private val opponentCode: Char, private val yourCode: Char, val points: Int) : Playable {
        ROCK('A', 'X', 1) {
            override fun getOutcomeAgainst(other: RockPaperScissors) =
                when (other) {
                    ROCK -> 3
                    SCISSORS -> 6
                    PAPER -> 0
                }
        },
        PAPER('B', 'Y', 2) {
            override fun getOutcomeAgainst(other: RockPaperScissors) =
                when (other) {
                    PAPER -> 3
                    ROCK -> 6
                    SCISSORS -> 0
                }
        },
        SCISSORS('C', 'Z', 3) {
            override fun getOutcomeAgainst(other: RockPaperScissors) =
                when (other) {
                    SCISSORS -> 3
                    PAPER -> 6
                    ROCK -> 0
                }
        };

        fun getPointsAgainst(other: RockPaperScissors) =
            points + getOutcomeAgainst(other)

        companion object {
            private val opponentCodeMap = values().associateBy { it.opponentCode }
            private val yourCodeMap = values().associateBy { it.yourCode }

            fun fromOpponentCode(opponentCode: Char) = opponentCodeMap[opponentCode]!!
            fun fromYourCode(yourCode: Char) = yourCodeMap[yourCode]!!
        }
    }

    interface Playable {
        fun getOutcomeAgainst(other: RockPaperScissors): Int
    }

    fun getResultPart1() {
        getInputAsSequence().map {
            val opponentChoice = RockPaperScissors.fromOpponentCode(it.first())
            val yourChoice = RockPaperScissors.fromYourCode(it[2])
            yourChoice.getPointsAgainst(opponentChoice)
        }.sum()
            .call { println(it) }
    }

    /*
    */

    fun getResultPart2() {
        fun getLosingChoice(opponentChoice: RockPaperScissors) =
            when (opponentChoice) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                else -> PAPER
            }

        fun getWinningChoice(opponentChoice: RockPaperScissors) =
            when (opponentChoice) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                else -> ROCK
            }

        getInputAsSequence().map {
            val opponentChoice = RockPaperScissors.fromOpponentCode(it.first())
            it[2].let { c ->
                when (c) {
                    'X' -> 0 + getLosingChoice(opponentChoice).points
                    'Y' -> 3 + opponentChoice.points
                    else -> 6 + getWinningChoice(opponentChoice).points
                }
            }
        }.sum()
            .call { println(it) }
    }
}
