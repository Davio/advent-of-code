package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.*
import kotlin.system.measureTimeMillis

fun main() {
    println(Day11.getResultPart1())
    measureTimeMillis {
        println(Day11.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day X](https://adventofcode.com/2022/day/X#part2])
 */
object Day11 {

    data class Monkey(
        val number: Int,
        val items: ArrayDeque<Long>,
        val operation: (Long) -> Long,
        val testNumber: Long,
        val trueMonkeyNumber: Int,
        val falseMonkeyNumber: Int
    ) : Comparable<Monkey> {
        var numberOfInspections: Int = 0

        override fun compareTo(other: Monkey) = numberOfInspections.compareTo(other.numberOfInspections)
    }

    fun getResultPart1(): Long {
        val monkeyMap = getInputAsSequence()
            .split { it.isBlank() }
            .map { parseMonkey(it) }
            .associateBy { it.number }

        val numberOfRounds = 20
        repeat(numberOfRounds) {
            monkeyMap.values.forEach { monkey ->
                do {
                    val worryLevel = monkey.items.removeFirstOrNull() ?: break
                    monkey.numberOfInspections++
                    val newWorryLevel = (monkey.operation.invoke(worryLevel)) / 3L
                    val newMonkeyNumber =
                        if (newWorryLevel % monkey.testNumber == 0L)
                            monkey.trueMonkeyNumber
                        else monkey.falseMonkeyNumber
                    monkeyMap[newMonkeyNumber]!!.items.addLast(newWorryLevel)
                } while (true)
            }
        }
        return monkeyMap.values.top(2).map { it.numberOfInspections.toLong() }.reduce(Long::times)
    }

    fun getResultPart2(): Long {
        val monkeyMap = getInputAsSequence()
            .split { it.isBlank() }
            .map { parseMonkey(it) }
            .associateBy { it.number }

        val lcm = monkeyMap.values.map { it.testNumber }.reduce { acc, n -> lcm(acc, n) }
        val numberOfRounds = 10000
        repeat(numberOfRounds) {
            monkeyMap.values.forEach { monkey ->
                do {
                    val worryLevel = monkey.items.removeFirstOrNull() ?: break
                    monkey.numberOfInspections++
                    val newWorryLevel = monkey.operation.invoke(worryLevel) % lcm
                    val newMonkeyNumber = if (newWorryLevel % monkey.testNumber == 0L) monkey.trueMonkeyNumber else monkey.falseMonkeyNumber
                    monkeyMap[newMonkeyNumber]!!.items.addLast(newWorryLevel)
                } while (true)
            }
        }
        return monkeyMap.values.top(2).map { it.numberOfInspections.toLong() }.reduce(Long::times)
    }

    private val monkeyLine1Regex = Regex("Monkey (\\d+):")
    private val monkeyLine2Regex = Regex("Starting items: (.+)")
    private val monkeyLine3Regex = Regex("Operation: new = old (.) (\\d+|old)")
    private val monkeyLine4Regex = Regex("Test: divisible by (\\d+)")
    private val monkeyLine5Regex = Regex("If true: throw to monkey (\\d+)")
    private val monkeyLine6Regex = Regex("If false: throw to monkey (\\d+)")

    private fun parseMonkey(lines: List<String>): Monkey {
        val number = monkeyLine1Regex.find(lines[0])!!.groupValues[1].toInt()
        val startingItems =
            ArrayDeque(monkeyLine2Regex.find(lines[1])!!.groupValues[1].split(", ").map { it.toLong() })
        val operation = monkeyLine3Regex.find(lines[2])!!.destructured.let { (operatorStr, operand) ->
            val operator: (Long, Long) -> Long = when (operatorStr) {
                "+" -> Long::plus
                "*" -> Long::times
                else -> throw IllegalArgumentException()
            }
            if (operand == "old") {
                { n1: Long -> operator.invoke(n1, n1) }
            } else {
                { n1: Long -> operator.invoke(n1, operand.toLong()) }
            }
        }
        val testNumber = monkeyLine4Regex.find(lines[3])!!.groupValues[1].toLong()
        val trueAction = monkeyLine5Regex.find(lines[4])!!.groupValues[1].toInt()
        val falseAction = monkeyLine6Regex.find(lines[5])!!.groupValues[1].toInt()

        return Monkey(number, startingItems, operation, testNumber, trueAction, falseAction)
    }
}
