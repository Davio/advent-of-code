package com.github.davio.aoc.general

import kotlin.math.abs

@JvmInline
value class Point(
    private val data: Pair<Int, Int> = 0 to 0,
) : Comparable<Point> {
    val x get() = data.first
    val y get() = data.second

    fun manhattanDistanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)

    operator fun plus(other: Point) = of(x + other.x, y + other.y)

    operator fun minus(other: Point) = of(x - other.x, y - other.y)

    operator fun times(n: Int) = of(x * n, y * n)

    fun up() = up(1)

    fun down() = down(1)

    fun left() = left(1)

    fun right() = right(1)

    infix fun up(amount: Int) = of(data.first, data.second - amount)

    infix fun down(amount: Int) = of(data.first, data.second + amount)

    infix fun left(amount: Int) = of(data.first - amount, data.second)

    infix fun right(amount: Int) = of(data.first + amount, data.second)

    operator fun rangeTo(endInclusive: Point) =
        sequence {
            if (x == endInclusive.x) {
                val firstPair = if (y < endInclusive.y) this@Point else endInclusive
                val secondPair = if (firstPair == this@Point) endInclusive else this@Point
                (firstPair.y..secondPair.y).forEach { y ->
                    yield(of(x, y))
                }
            } else if (y == endInclusive.y) {
                val firstPair = if (x < endInclusive.x) this@Point else endInclusive
                val secondPair = if (firstPair == this@Point) endInclusive else this@Point
                (firstPair.x..secondPair.x).forEach { x ->
                    yield(of(x, y))
                }
            } else {
                val firstPair = if (x < endInclusive.x) this@Point else endInclusive
                val secondPair = if (firstPair == this@Point) endInclusive else this@Point

                val yInc = if (firstPair.y < secondPair.y) 1 else -1
                var y = firstPair.y
                (firstPair.x..secondPair.x).forEach { x ->
                    yield(of(x, y))
                    y += yInc
                }
            }
        }

    override fun toString(): String = "$x,$y"

    override fun compareTo(other: Point): Int = Comparator.comparingInt(Point::x).thenComparingInt(Point::y).compare(this, other)

    companion object {
        fun of(
            x: Int,
            y: Int,
        ) = if (x == 0 && y == 0) ZERO else Point(x to y)

        val RIGHT = Vector.of(1, 0)
        val LEFT = Vector.of(-1, 0)
        val UP = Vector.of(0, -1)
        val DOWN = Vector.of(0, 1)
        val RIGHT_DOWN = Vector.of(1, 1)
        val RIGHT_UP = Vector.of(1, -1)
        val LEFT_DOWN = Vector.of(-1, 1)
        val LEFT_UP = Vector.of(-1, -1)

        val ZERO = Point(0 to 0)
    }
}

fun String.toPoint(): Point = this.trim().split(",").let { Point.of(it[0].toInt(), it[1].toInt()) }

typealias Vector = Point
