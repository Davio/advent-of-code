package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day X](https://adventofcode.com/2023/day/2#part2])
 */
object Day2 : Day() {
    private val bag =
        mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )

    override fun part1(): Long {
        val games =
            getInputAsList()
                .map { it.toGame() }
                .toList()

        return games.filter { it.isPossible() }.sumOf { it.id }
    }

    override fun part2(): Long = getInputAsList().sumOf { it.toGame().getPower() }

    private fun String.toGame(): Game {
        val id = substringBefore(":").substringAfter("Game ").toLong()
        return Game(id).also { game ->
            substringAfter(": ").split("; ").forEach { cubePart ->
                val subset = subsetOf(cubePart)
                game.subsets.add(subset)
            }
        }
    }

    private data class Game(
        val id: Long,
    ) {
        val subsets: MutableList<Map<String, Int>> = mutableListOf()

        fun isPossible(): Boolean = subsets.all { subsetIsPossible(it) }

        fun getPower(): Long {
            val minimumCubes: MutableMap<String, Long> = mutableMapOf()
            subsets.forEach { subset ->
                subset.entries.forEach { entry ->
                    if (entry.value > minimumCubes.getOrPut(entry.key) { entry.value.toLong() }) {
                        minimumCubes[entry.key] = entry.value.toLong()
                    }
                }
            }
            return minimumCubes.values.reduce { acc, e -> acc * e }
        }

        private fun subsetIsPossible(subset: Map<String, Int>): Boolean = subset.all { bag.getValue(it.key) >= it.value }
    }

    private fun subsetOf(part: String): Map<String, Int> =
        part.split(", ").associate {
            val (number, color) = it.split(" ")
            color to number.toInt()
        }
}
