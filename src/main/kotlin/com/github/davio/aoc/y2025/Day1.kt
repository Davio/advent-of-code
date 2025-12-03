package com.github.davio.aoc.y2025

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLines
import kotlin.math.abs

/**
 * See [Advent of Code 2025 Day 1](https://adventofcode.com/2025/day/1)
 */
class Day1(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private var startPosition = 50
    private lateinit var rotations: List<Int>

    override fun part1(): Int {
        println("The dial starts by pointing at $startPosition")
        return rotations
            .runningFold(startPosition) { acc, rotation ->
                (acc + rotation).mod(100).also {
                    println("The dial is rotated $rotation to point at $it")
                }
            }.count { it == 0 }
    }

    override fun part2(): Int =
        rotations
            .fold(startPosition to 0) { (position, hitZero), rotation ->
                val loops = abs(rotation / 100)
                val newPosition = (position + rotation).mod(100)
                val minimalRotationHitsZero =
                    newPosition == 0 ||
                        if (rotation < 0) {
                            newPosition >= position && position != 0
                        } else {
                            newPosition <= position
                        }
                val rotationHitZero = (if (minimalRotationHitsZero) 1 else 0) + loops
                val rotationText = if (rotation < 0) "L${abs(rotation)}" else "R$rotation"
                println(
                    "Position: $position, " +
                        "rotation: $rotationText, " +
                        "new position: $newPosition, " +
                        "loops: $loops, " +
                        "hitZero: $rotationHitZero",
                )
                newPosition to hitZero + rotationHitZero
            }.second

    override fun parseInput() {
        rotations =
            getInputAsLines().map {
                val rotationChar = it[0]
                val amount = it.substring(1).toInt()
                if (rotationChar == 'L') -amount else amount
            }
    }
}
