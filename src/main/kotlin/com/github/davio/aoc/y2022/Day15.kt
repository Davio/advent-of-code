package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import kotlin.system.measureTimeMillis

fun main() {
    Day15.initialize()
    println(Day15.getResultPart1())
    measureTimeMillis {
        println(Day15.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 15](https://adventofcode.com/2022/day/15#part2])
 */
object Day15 {

    private lateinit var sensors: Set<Sensor>
    private lateinit var sensorPoints: Set<Point>
    private lateinit var beaconPoints: Set<Point>

    private var minX = 0
    private var maxX = 0
    private var minY = 0
    private var maxY = 0

    data class Sensor(val point: Point, val closestBeacon: Point)

    fun initialize() {
        sensors = getInputAsList()
            .map { parseLineAsSensor((it)) }
            .toSet()

        sensorPoints = sensors.map { it.point }.toSet()
        beaconPoints = sensors.map { it.closestBeacon }.toSet()
        val allPoints = sensorPoints + beaconPoints

        minX = allPoints.minOf { it.x }
        maxX = allPoints.maxOf { it.x }
        minY = allPoints.minOf { it.y }
        maxY = allPoints.maxOf { it.y }
    }

    private val lineRegex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
    private fun parseLineAsSensor(line: String): Sensor {
        val (sX, sY, bX, bY) = lineRegex.matchEntire(line)!!.destructured
        return Sensor(Point.of(sX.toInt(), sY.toInt()), Point.of(bX.toInt(), bY.toInt()))
    }

    fun getResultPart1(): Int {
        val y = 2_000_000
        return (minX..maxX).count { x ->
            sensors.any { sensor ->
                val point = Point.of(x, y)
                !sensorPoints.contains(point) && !beaconPoints.contains(point) && isInsideSensorToClosestBeaconRegion(sensor, point)
            }
        }
    }

    fun getResultPart2(): Long {
        val maxSearchIndex = 4_000_000
        val multiplier = 4_000_000L
        val beaconPoint = sensors.asSequence().flatMap {
            getPointsOutsideBeaconRange(it, maxSearchIndex)
        }.first { point ->
            pointIsUnknownBeacon(point)
        }
        return beaconPoint.x * multiplier + beaconPoint.y
    }

    private fun pointIsUnknownBeacon(point: Point): Boolean {
        if (sensorPoints.contains(point) || beaconPoints.contains(point)) {
            return false
        }

        return sensors.none { sensor -> isInsideSensorToClosestBeaconRegion(sensor, point) }
    }

    private fun isInsideSensorToClosestBeaconRegion(sensor: Sensor, point: Point): Boolean {
        val manhattanDistance = sensor.point.manhattanDistanceTo(sensor.closestBeacon)
        return point.manhattanDistanceTo(sensor.point) <= manhattanDistance
    }

    private fun getPointsOutsideBeaconRange(sensor: Sensor, maxSearchIndex: Int): Sequence<Point> {
        val sensorPoint = sensor.point
        val pointDistanceJustOutOfRange = sensorPoint.manhattanDistanceTo(sensor.closestBeacon) + 1

        return sequence {
            val minY = sensorPoint.y - pointDistanceJustOutOfRange
            val maxY = sensorPoint.y + pointDistanceJustOutOfRange
            (minY..maxY).forEach { y ->
                if (y == minY || y == maxY) {
                    val point = Point.of(sensorPoint.x, y)
                    if (isValidNonBeaconPoint(point, sensor, maxSearchIndex)) yield(point)
                } else {
                    var yOffset = y - minY
                     val pointLeft: Point
                    val pointRight: Point
                    if (y < sensorPoint.y) {
                        pointLeft = Point.of(sensorPoint.x - yOffset, y)
                        pointRight = Point.of(sensorPoint.x + yOffset, y)
                    } else if (y == sensorPoint.y) {
                        pointLeft = Point.of(sensorPoint.x - pointDistanceJustOutOfRange, y)
                        pointRight = Point.of(sensorPoint.x + pointDistanceJustOutOfRange, y)
                    } else {
                        yOffset = maxY - y
                        pointLeft = Point.of(sensorPoint.x - yOffset, y)
                        pointRight = Point.of(sensorPoint.x + yOffset, y)
                    }
                    if (isValidNonBeaconPoint(pointLeft, sensor, maxSearchIndex)) yield(pointLeft)
                    if (isValidNonBeaconPoint(pointRight, sensor, maxSearchIndex)) yield(pointRight)
                }
            }
        }
    }

    private fun isValidNonBeaconPoint(point: Point, sensor: Sensor, maxSearchIndex: Int) =
        !sensorPoints.contains(point) && point != sensor.closestBeacon
                && point.x >= 0 && point.x <= maxSearchIndex
                && point.y >= 0 && point.y <= maxSearchIndex
}
