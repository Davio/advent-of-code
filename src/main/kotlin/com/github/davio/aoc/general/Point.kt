package com.github.davio.aoc.general

data class Point(val x: Int = 0, val y: Int = 0) : Comparable<Point> {

    operator fun plus(other: Point) = Point(this.x + other.x, this.y + other.y)
    operator fun minus(other: Point) = Point(this.x - other.x, this.y - other.y)

    override fun toString(): String {
        return "$x,$y"
    }

    override fun compareTo(other: Point): Int {
        val byX = this.x.compareTo(other.x)
        if (byX != 0) return byX
        return this.y.compareTo(other.y)
    }

    companion object {
        val ZERO = Point(0, 0)
    }
}

fun String.toPoint(): Point = this.split(",").let { Point(it[0].toInt(), it[1].toInt()) }

typealias Vector = Point

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)

operator fun Point.rangeTo(endInclusive: Point) = sequence {
    val startInclusive = this@rangeTo
    if (startInclusive.x == endInclusive.x) {
        val firstPair = if (startInclusive.y < endInclusive.y) startInclusive else endInclusive
        val secondPair = if (firstPair == startInclusive) endInclusive else startInclusive
        (firstPair.y..secondPair.y).forEach { y ->
            yield(Point(startInclusive.x, y))
        }
    } else if (startInclusive.y == endInclusive.y) {
        val firstPair = if (startInclusive.x < endInclusive.x) startInclusive else endInclusive
        val secondPair = if (firstPair == startInclusive) endInclusive else startInclusive
        (firstPair.x..secondPair.x).forEach { x ->
            yield(Point(x, startInclusive.y))
        }
    } else {
        val firstPair = if (startInclusive.x < endInclusive.x) startInclusive else endInclusive
        val secondPair = if (firstPair == startInclusive) endInclusive else startInclusive

        val yInc = if (firstPair.y < secondPair.y) 1 else -1
        var y = firstPair.y
        (firstPair.x..secondPair.x).forEach { x ->
            yield(Point(x, y))
            y += yInc
        }
    }
}
