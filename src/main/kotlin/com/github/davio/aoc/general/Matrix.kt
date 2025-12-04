package com.github.davio.aoc.general

data class Matrix<T>(
    val data: List<MutableList<T>>,
) {
    val maxX: Int = data.lastIndex
    val maxY: Int = data[0].lastIndex

    val rows: List<List<T>> get() = data.toList()
    val columns: List<List<T>>
        get() =
            data[0].indices.map { x ->
                data.indices
                    .map { y ->
                        data[y][x]
                    }.toList()
            }

    val size: Int = rows.size * columns.size

    operator fun get(
        x: Int,
        y: Int,
    ): T = data[y][x]

    operator fun get(p: Point): T = data[p.y][p.x]

    operator fun set(
        p: Point,
        value: T,
    ) {
        data[p.y][p.x] = value
    }

    fun getAdjacentPoints(p: Point): Sequence<Point> {
        if (data.isEmpty()) return emptySequence()
        if (p.x !in (0..maxX) || p.y !in (0..maxY)) return emptySequence()

        return sequence {
            ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(maxY)).forEach { y ->
                ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(maxX)).forEach { x ->
                    val otherPoint = Point.of(x, y)
                    if (otherPoint != p) {
                        yield(otherPoint)
                    }
                }
            }
        }
    }

    fun getAdjacentPointsWithValues(p: Point): Sequence<PointIndexedValue<T>> =
        getAdjacentPoints(p).map {
            PointIndexedValue(it, this[it])
        }

    fun getAdjacentValues(p: Point): Sequence<T> =
        getAdjacentPoints(p).map {
            this[it]
        }

    fun getOrthogonallyAdjacentPoints(p: Point): Sequence<Point> {
        if (data.isEmpty()) return emptySequence()
        if (p.x !in (0..maxX) || p.y !in (0..maxY)) return emptySequence()

        return sequence {
            ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(maxY)).forEach { y ->
                ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(maxX)).forEach { x ->
                    val otherPoint = Point.of(x, y)
                    if ((y == p.y || x == p.x) && otherPoint != p) {
                        yield(Point.of(x, y))
                    }
                }
            }
        }
    }

    fun getPoints(): Sequence<Point> {
        if (data.isEmpty()) return emptySequence()
        if (maxX == 0 && maxY == 0) return sequenceOf(Point.ZERO)

        return data.indices.asSequence().flatMap { y ->
            data[0].indices.asSequence().map { x ->
                Point.of(x, y)
            }
        }
    }

    fun getPointsWithValues(): Sequence<PointIndexedValue<T>> =
        getPoints().map {
            PointIndexedValue(it, this[it])
        }

    override fun toString(): String = data.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = " ") }

    fun toString(transform: (T) -> CharSequence): String =
        data.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = "", transform = transform) }
}

data class PointIndexedValue<out T>(
    val point: Point,
    val value: T,
)
