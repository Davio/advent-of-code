package com.github.davio.aoc.general

data class Point(val x: Int = 0, val y: Int = 0) : Comparable<Point> {

    operator fun plus(other: Point) = Point(this.x + other.x, this.y + other.y)
    operator fun minus(other: Point) = Point(this.x - other.x, this.y - other.y)

    operator fun rangeTo(endInclusive: Point) = sequence {
        if (x == endInclusive.x) {
            val firstPair = if (y < endInclusive.y) this@Point else endInclusive
            val secondPair = if (firstPair == this@Point) endInclusive else this@Point
            (firstPair.y..secondPair.y).forEach { y ->
                yield(Point(x, y))
            }
        } else if (y == endInclusive.y) {
            val firstPair = if (x < endInclusive.x) this@Point else endInclusive
            val secondPair = if (firstPair == this@Point) endInclusive else this@Point
            (firstPair.x..secondPair.x).forEach { x ->
                yield(Point(x, y))
            }
        } else {
            val firstPair = if (x < endInclusive.x) this@Point else endInclusive
            val secondPair = if (firstPair == this@Point) endInclusive else this@Point

            val yInc = if (firstPair.y < secondPair.y) 1 else -1
            var y = firstPair.y
            (firstPair.x..secondPair.x).forEach { x ->
                yield(Point(x, y))
                y += yInc
            }
        }
    }

    override fun toString(): String = "$x,$y"

    override fun compareTo(other: Point): Int =
        Comparator.comparingInt(Point::x).thenComparingInt(Point::y).compare(this, other)

    companion object {
        val ZERO = Point(0, 0)
    }
}

fun String.toPoint(): Point = this.split(",").let { Point(it[0].toInt(), it[1].toInt()) }

typealias Vector = Point
