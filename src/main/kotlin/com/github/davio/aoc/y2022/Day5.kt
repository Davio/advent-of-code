package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.general.split
import kotlin.system.measureTimeMillis

fun main() {
    println(Day5.getResultPart1())
    measureTimeMillis {
        println(Day5.getResultPart2())
    }.also { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 5](https://adventofcode.com/2022/day/5#part2])
 */
object Day5 : Day() {

    private data class Move(val amount: Int, val from: Int, val to: Int)

    private fun parseInput(): Pair<List<MutableList<String>>, List<Move>> {
        val parts = getInputAsSequence()
            .split(String::isBlank)
            .toList()
        val stackLines = parts.first()
        val noOfColumns = stackLines.last().split(" ").last().toInt()

        val stacks = (0 until noOfColumns).map {
            mutableListOf<String>()
        }.toList()

        stackLines.take(stackLines.size - 1).forEach { line ->
            repeat(noOfColumns) { columnNo ->
                val startIndex = columnNo * 3 + columnNo + 1
                if (startIndex > line.lastIndex) {
                    return@repeat
                }
                val crate = line.substring(startIndex, startIndex + 1)
                if (crate != " ") {
                    stacks[columnNo].add(crate)
                }
            }
        }

        val moveRegex = Regex("""move (\d+) from (\d+) to (\d+)""")
        val moves = parts.last().map { line ->
            val (amount, from, to) = moveRegex.matchEntire(line)!!.destructured
            Move(amount.toInt(), from.toInt(), to.toInt())
        }

        return stacks to moves
    }

    fun getResultPart1(): String {
        val (stacks, moves) = parseInput()

        fun moveCrates(move: Move) {
            repeat(move.amount) {
                stacks[move.to - 1].add(0, stacks[move.from - 1].removeAt(0))
            }
        }

        moves.forEach { moveCrates(it) }
        return stacks.joinToString(separator = "") { it.firstOrNull() ?: "" }
    }

    fun getResultPart2(): String {
        val (stacks, moves) = parseInput()

        fun moveCrates(move: Move) {
            val fromStack = stacks[move.from - 1]
            stacks[move.to - 1].addAll(0, fromStack.take(move.amount))
            repeat(move.amount) {
                fromStack.removeFirst()
            }
        }

        moves.forEach { moveCrates(it) }
        return stacks.joinToString(separator = "") { it.firstOrNull() ?: "" }
    }
}
