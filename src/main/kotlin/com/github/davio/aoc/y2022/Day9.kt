package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.*
import kotlin.math.abs
import kotlin.math.sign
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        println(Day9.getResultPart1())
    }.call { println("Took $it ms") }
    measureTimeMillis {
        println(Day9.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 9](https://adventofcode.com/2022/day/9#part2])
 */
object Day9 : Day() {

    private enum class Direction(val char: Char, val vector: Vector) {
        UP('U', Point.of(0, 1)),
        RIGHT('R', Point.of(1, 0)),
        DOWN('D', Point.of(0, -1)),
        LEFT('L', Point.of(-1, 0));

        companion object {
            private val map = entries.associateBy { it.char }
            fun fromChar(c: Char) = map[c]!!
        }
    }

    private data class Move(val direction: Direction, val amount: Int)

    fun getResultPart1() = getInputAsSequence()
        .map { parseMove(it) }
        .fold(Triple(mutableSetOf(Point.ZERO), Point.ZERO, Point.ZERO)) { acc, move ->
            var head = acc.second
            var tail = acc.third
            repeat(move.amount) {
                head += move.direction.vector
                val tailMovement = getKnotMovement(head, tail)
                if (tailMovement != Vector.ZERO) {
                    tail += tailMovement
                    acc.first.add(tail)
                }
            }
            Triple(acc.first, head, tail)
        }.first.size

    fun getResultPart2() =
        getInputAsSequence()
            .map { parseMove(it) }
            .fold(Pair(mutableSetOf(Point.ZERO), MutableList(10) { Point.ZERO })) { acc, move ->
                val knots = acc.second
                repeat(move.amount) {
                    knots[0] += move.direction.vector
                    for (index in knots.indices.drop(1)) {
                        val knotMovement = getKnotMovement(knots[index - 1], knots[index])
                        if (knotMovement == Point.ZERO) break
                        knots[index] += knotMovement
                    }
                    acc.first.add(knots.last())
                }
                Pair(acc.first, knots)
            }.first.size

    private fun parseMove(line: String) = line.split(" ").let { Move(Direction.fromChar(it[0][0]), it[1].toInt()) }

    private fun getKnotMovement(knotInFront: Point, knot: Point): Vector {
        if (knotInFront == knot || (abs(knotInFront.x - knot.x) <= 1 && abs(knotInFront.y - knot.y) <= 1)) return Vector.ZERO
        return Point.of((knotInFront.x - knot.x).sign, (knotInFront.y - knot.y).sign)
    }
}
