package com.github.davio.aoc.y2024

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Matrix
import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.Vector
import com.github.davio.aoc.general.getInputAsMatrix

/**
 * See [Advent of Code 2024 Day 4](https://adventofcode.com/2024/day/4)
 */
class Day4(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var wordsearch: Matrix<Char>

    override fun part1(): Int =
        wordsearch.getPoints().filter { wordsearch[it] == 'X' }.sumOf {
            findXmases(it)
        }

    override fun part2(): Int =
        wordsearch.getPoints().filter { wordsearch[it] == 'A' }.count {
            hasMasCross(it)
        }

    //region part 1
    private fun findXmases(point: Point): Int =
        arrayOf(
            isXmasHorizontal(point),
            isXmasHorizontalBackwards(point),
            isXmasVertical(point),
            isXmasVerticalBackwards(point),
            isXmasDiagonalRightDown(point),
            isXmasDiagonalRightUp(point),
            isXmasDiagonalLeftUp(point),
            isXmasDiagonalLeftDown(point),
        ).count { it }

    private fun isXmasHorizontal(point: Point): Boolean =
        point.x <= wordsearch.maxX - 3 &&
            isMas(point, Vector.RIGHT)

    private fun isXmasHorizontalBackwards(point: Point): Boolean =
        point.x >= 3 &&
            isMas(point, Vector.LEFT)

    private fun isXmasVertical(point: Point): Boolean =
        point.y <= wordsearch.maxY - 3 &&
            isMas(point, Vector.DOWN)

    private fun isXmasVerticalBackwards(point: Point): Boolean =
        point.y >= 3 &&
            isMas(point, Vector.UP)

    private fun isXmasDiagonalRightDown(point: Point): Boolean =
        point.x <= wordsearch.maxX - 3 &&
            point.y <= wordsearch.maxY - 3 &&
            isMas(point, Vector.RIGHT_DOWN)

    private fun isXmasDiagonalRightUp(point: Point): Boolean =
        point.x <= wordsearch.maxX - 3 &&
            point.y >= 3 &&
            isMas(point, Vector.RIGHT_UP)

    private fun isXmasDiagonalLeftDown(point: Point): Boolean =
        point.x >= 3 &&
            point.y <= wordsearch.maxY - 3 &&
            isMas(point, Vector.LEFT_DOWN)

    private fun isXmasDiagonalLeftUp(point: Point): Boolean =
        point.x >= 3 &&
            point.y >= 3 &&
            isMas(point, Vector.LEFT_UP)

    private fun isMas(
        startingPoint: Point,
        direction: Vector,
    ): Boolean =
        wordsearch[startingPoint + direction] == 'M' &&
            wordsearch[startingPoint + (direction * 2)] == 'A' &&
            wordsearch[startingPoint + (direction * 3)] == 'S'
    //endregion

    //region part2
    private fun hasMasCross(point: Point): Boolean =
        point.x > 0 &&
            point.x < wordsearch.maxX &&
            point.y > 0 &&
            point.y < wordsearch.maxX &&
            isMasOrReversed(point + Vector.LEFT_UP, point + Vector.RIGHT_DOWN) &&
            isMasOrReversed(point + Vector.RIGHT_UP, point + Vector.LEFT_DOWN)

    private fun isMasOrReversed(
        point1: Point,
        point2: Point,
    ): Boolean =
        (wordsearch[point1] == 'M' && wordsearch[point2] == 'S') ||
            (wordsearch[point1] == 'S' && wordsearch[point2] == 'M')
    //endregion

    override fun parseInput() {
        wordsearch = getInputAsMatrix()
    }
}
