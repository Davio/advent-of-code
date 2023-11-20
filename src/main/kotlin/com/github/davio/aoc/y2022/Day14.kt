package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.toPoint
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    Day14.initialize()
    println(Day14.getResultPart1())
    measureTimeMillis {
        println(Day14.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 14](https://adventofcode.com/2022/day/14#part2])
 */
object Day14 {

    @JvmInline
    value class Path(private val points: List<Point>) {
        fun getStraightLines() = points.zipWithNext()
    }

    private lateinit var scan : List<List<Pair<Point, Point>>>
    private var minX: Int = 0
    private var maxX: Int = 0
    private var maxY: Int = 0
    private lateinit var lines: List<Pair<Point, Point>>
    private lateinit var blockedPoints: Set<Point>
    private lateinit var sandAtRest: MutableSet<Point>
    private var currentSand: Point = Point.ZERO

    fun getResultPart1(): Int {
        sandAtRest = hashSetOf()
        currentSand = Point.of(500, 0)

        do {
//            printScan(blockedPoints, sandAtRest, currentSand, minX, maxX, maxY)
            currentSand = doStep(blockedPoints, sandAtRest, currentSand, maxY + 3)
        } while(currentSand.y < maxY)

        return sandAtRest.size
    }

    fun getResultPart2(): Int {
        sandAtRest = hashSetOf()
        currentSand = Point.of(500, 0)

        do {
            currentSand = doStep(blockedPoints, sandAtRest, currentSand, maxY + 2)
            if (currentSand.x < minX) minX = currentSand.x
            if (currentSand.x > maxX) maxX = currentSand.x
        } while(!sandAtRest.contains(Point.of(500, 0)))

        return sandAtRest.size
    }

    fun initialize() {
        scan = getInputAsList()
            .map { parseLine(it).getStraightLines() }
        minX = scan.minOf { path -> path.minOf { line -> min(line.first.x, line.second.x) } }
        maxX = scan.maxOf { path -> path.maxOf { line -> max(line.first.x, line.second.x) } }
        maxY = scan.maxOf { path -> path.maxOf { line -> max(line.first.y, line.second.y) } }
        lines = scan.flatten()
        blockedPoints = (0..maxY).flatMap { y ->
            (minX..maxX).map { x ->
                Point.of(x, y)
            }
        }.filter { p ->
            lines.any { lineContains(it, p) }
        }.toSet()
    }

    private fun doStep(blockedPoints: Set<Point>, sandAtRest: MutableSet<Point>, currentSand: Point, maxY: Int): Point {
        val pointBelow = Point.of(currentSand.x, currentSand.y + 1)
        if (pointBelow.y == maxY) {
            sandAtRest.add(currentSand)
            return Point.of(500, 0)
        }

        if (!blockedPoints.contains(pointBelow) && !sandAtRest.contains(pointBelow)) {
            return pointBelow
        } else {
            val pointBelowAndLeft = Point.of(currentSand.x - 1, currentSand.y + 1)
            return if (!blockedPoints.contains(pointBelowAndLeft) && !sandAtRest.contains(pointBelowAndLeft)) {
                pointBelowAndLeft
            } else {
                val pointBelowAndRight = Point.of(currentSand.x + 1, currentSand.y + 1)
                if (!blockedPoints.contains(pointBelowAndRight) && !sandAtRest.contains(pointBelowAndRight)) {
                    pointBelowAndRight
                } else {
                    sandAtRest.add(currentSand)
                    Point.of(500, 0)
                }
            }
        }
    }

    private fun parseLine(line: String): Path =
        Path(line.split(" -> ").map { it.toPoint() })

    private fun lineContains(line: Pair<Point, Point>, point: Point): Boolean {
        if (line.first.x == line.second.x) {
            return point.x == line.first.x && point.y in (min(line.first.y, line.second.y)..max(line.first.y, line.second.y))
        }
        return point.y == line.first.y && point.x in (min(line.first.x, line.second.x)..max(line.first.x, line.second.x))
    }

    private fun printScan(blockedPoints: Set<Point>, sandAtRest: Set<Point>, currentSand: Point, minX: Int, maxX: Int, maxY: Int) {
        println()
        repeat(3) { index ->
            print("  ")
            print(minX.toString()[index])
            repeat(500 - minX - 1) { print(" ") }
            print(if (index == 0) "5" else "0")
            repeat(maxX - 500 - 1) { print(" ") }
            print(maxX.toString()[index])
            println()
        }
        (0..maxY).forEach { y ->
            print("$y ")
            (minX..maxX).forEach { x ->
                val point = Point.of(x, y)
                if (currentSand == point) {
                    print("+")
                } else if (sandAtRest.contains(point)) {
                    print("o")
                } else if (blockedPoints.contains(point)) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
//        print("\u001b[14F")
    }
}
