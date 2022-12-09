package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.Vector
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import kotlin.math.abs
import kotlin.math.sign
import kotlin.system.measureTimeMillis

fun main() {
    println(Day9.getResultPart1())
    measureTimeMillis {
        println(Day9.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 9](https://adventofcode.com/2022/day/9#part2])
 */
object Day9 {

    private enum class Direction(val char: Char, val vector: Vector) {
        UP('U', Vector(0, 1)),
        RIGHT('R', Vector(1, 0)),
        DOWN('D', Vector(0, -1)),
        LEFT('L', Vector(-1, 0));

        companion object {
            private val map = values().associateBy { it.char }
            fun fromChar(c: Char) = map[c]!!
        }
    }

    private data class Move(val direction: Direction, val amount: Int)

    fun getResultPart1(): Int {
        var head = Point(0, 0)
        var tail = Point(0, 0)
        val tailPositions = mutableSetOf(tail)

        getInputAsSequence()
            .map { parseMove(it) }
            .forEach { move ->
                repeat(move.amount) {
                    head += move.direction.vector
                    val tailMovement = getKnotMovement(head, tail)
                    if (tailMovement != Vector.ZERO) {
                        tail += tailMovement
                        tailPositions.add(tail.copy())
                    }
                }
            }

        return tailPositions.size
    }

    fun getResultPart2(): Int {
        val knots = MutableList(10) { Point.ZERO }
        val tailPositions = mutableSetOf(knots.last())

        getInputAsSequence()
            .map { parseMove(it) }
            .forEach { move ->
                repeat(move.amount) {
                    knots[0] += move.direction.vector
                    knots.indices.drop(1).forEach { index ->
                        val knotMovement = getKnotMovement(knots[index - 1], knots[index])
                        if (knotMovement != Point.ZERO) {
                            knots[index] += knotMovement
                        }
                    }
                    tailPositions.add(knots.last())
                }
            }

        return tailPositions.size
    }

    private fun parseMove(line: String) = line.split(" ").let { Move(Direction.fromChar(it[0][0]), it[1].toInt()) }

    private fun getKnotMovement(knotInFront: Point, knot: Point): Vector {
        if (knotInFront == knot || (abs(knotInFront.x - knot.x) <= 1 && abs(knotInFront.y - knot.y) <= 1)) return Vector.ZERO
        return Vector((knotInFront.x - knot.x).sign, (knotInFront.y - knot.y).sign)
    }
}
