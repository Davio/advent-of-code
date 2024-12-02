package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.getInputAsMatrix
import com.github.davio.aoc.general.takeUntil
import com.github.davio.aoc.y2023.Day10.Pipe.Companion.toPipe
import com.github.davio.aoc.y2023.Day10.Pipe.entries

/**
 * See [Advent of Code 2023 Day 10](https://adventofcode.com/2023/day/10#part2])
 */
@OptIn(ExperimentalStdlibApi::class)
class Day10(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private val input = getInputAsMatrix { c -> c.toPipe() }
    private val start = input.getPoints().first { input[it] == Pipe.START }
    private var route1Point: Point? = null
    private var route2Point: Point? = null

    init {
        entries.filter { it != Pipe.START && it != Pipe.GROUND }.takeUntil {
            val (p1, p2) =
                when (it) {
                    Pipe.VERTICAL -> start.up() to start.down()
                    Pipe.HORIZONTAL -> start.left() to start.right()
                    Pipe.L_SHAPE -> start.up() to start.right()
                    Pipe.J_SHAPE -> start.up() to start.left()
                    Pipe.SEVEN_SHAPE -> start.left() to start.down()
                    Pipe.F_SHAPE -> start.right() to start.down()
                    else -> error("")
                }

            if (p1 !in input.getPoints() || p2 !in input.getPoints()) return@takeUntil false

            val nextP1 = getNextPoint(start, p1)
            val nextP2 = getNextPoint(start, p2)
            (
                nextP1 != null &&
                    nextP2 != null &&
                    getNextPoint(nextP1, p1) == start &&
                    getNextPoint(nextP2, p2) == start
            ).also { match ->
                if (match) {
                    route1Point = p1
                    route2Point = p2
                }
            }
        }
    }

    override fun part1(): Long {
        var steps = 1L
        var route1Previous = start
        var route2Previous = start

        while (true) {
            if ((route1Point == route2Previous && route2Point == route1Previous) ||
                (route1Point == route2Point)
            ) {
                return steps
            }

            steps++

            val tempRoute1 = route1Point
            val tempRoute2 = route2Point
            route1Point = getNextPoint(route1Previous, route1Point!!)
            route2Point = getNextPoint(route2Previous, route2Point!!)
            route1Previous = tempRoute1!!
            route2Previous = tempRoute2!!
        }
    }

    override fun part2(): Long {
        val route: MutableSet<Point> = mutableSetOf(start)

        var route1Previous = start
        var route2Previous = start

        while (!(route1Point == route2Previous && route2Point == route1Previous) && route1Point != route2Point) {
            route.add(route1Point!!)
            route.add(route2Point!!)

            val tempRoute1 = route1Point
            val tempRoute2 = route2Point
            route1Point = getNextPoint(route1Previous, route1Point!!)
            route2Point = getNextPoint(route2Previous, route2Point!!)
            route1Previous = tempRoute1!!
            route2Previous = tempRoute2!!
        }

        val enclosedPoints: MutableSet<Point> = mutableSetOf()
        input.getPoints().filter { input[it] == Pipe.GROUND }.forEach {
            if (it !in enclosedPoints) {
                val region = getPointsInRegion(it, mutableSetOf(it))
                enclosedPoints.addAll(region)
            }
        }

        return enclosedPoints.count().toLong()
    }

    private fun getPointsInRegion(
        point: Point,
        region: MutableSet<Point>,
    ): Set<Point> {
        val left = point.left()
        val down = point.down()
        val right = point.right()
        val otherPoints =
            mutableSetOf(left, down, right)
                .filter {
                    it !in region && it in input.getPoints() && input[it] == Pipe.GROUND
                }.toSet()

        if (otherPoints.isEmpty()) {
            return otherPoints
        }

        return region + otherPoints.flatMap { getPointsInRegion(it, region) }
    }

    private fun getNextPoint(
        previousPoint: Point,
        currentPoint: Point,
    ): Point? =
        when (input[currentPoint]) {
            Pipe.VERTICAL -> if (previousPoint.y < currentPoint.y) currentPoint.down() else currentPoint.up()
            Pipe.HORIZONTAL -> if (previousPoint.x < currentPoint.x) currentPoint.right() else currentPoint.left()
            Pipe.L_SHAPE -> if (previousPoint.y < currentPoint.y) currentPoint.right() else currentPoint.up()
            Pipe.J_SHAPE -> if (previousPoint.y < currentPoint.y) currentPoint.left() else currentPoint.up()
            Pipe.SEVEN_SHAPE -> if (previousPoint.x < currentPoint.x) currentPoint.down() else currentPoint.left()
            Pipe.F_SHAPE -> if (previousPoint.x > currentPoint.x) currentPoint.down() else currentPoint.right()
            Pipe.GROUND -> null
            else -> error("")
        }

    private enum class Pipe(
        val symbol: Char,
    ) {
        START('S'),
        VERTICAL('|'),
        HORIZONTAL('-'),
        L_SHAPE('L'),
        J_SHAPE('J'),
        SEVEN_SHAPE('7'),
        F_SHAPE('F'),
        GROUND('.'),
        ;

        companion object {
            @OptIn(ExperimentalStdlibApi::class)
            fun Char.toPipe() = entries.first { it.symbol == this@toPipe }
        }
    }
}
