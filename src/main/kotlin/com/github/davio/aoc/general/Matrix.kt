package com.github.davio.aoc.general

data class Matrix<T>(
    val data: List<MutableList<T>>,
) {
    val height: Int get() = data.size
    val width: Int get() = data[0].size
    val lastXIndex: Int get() = data.lastIndex
    val lastYIndex: Int get() = data[0].lastIndex

    val rows get(): List<List<T>> = data
    val columns: List<List<T>>
        get() =
            data[0].indices.map { x ->
                data.indices
                    .map { y ->
                        this[x, y]
                    }
            }

    val size: Int get() = width * height

    operator fun get(
        x: Int,
        y: Int,
    ): T = data[y][x]

    operator fun get(p: Point): T = get(p.x, p.y)

    operator fun set(
        p: Point,
        value: T,
    ) {
        set(p.x, p.y, value)
    }

    operator fun set(
        x: Int,
        y: Int,
        value: T,
    ) {
        data[y][x] = value
    }

    fun getRow(index: Int): MutableList<T> = data[index]

    fun getColumn(index: Int): List<T> = columns[index]

    fun getAdjacentPoints(p: Point): Sequence<Point> {
        if (data.isEmpty()) return emptySequence()
        if (p.x !in (0..lastXIndex) || p.y !in (0..lastYIndex)) return emptySequence()

        return sequence {
            ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(lastYIndex)).forEach { y ->
                ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(lastXIndex)).forEach { x ->
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
        if (p.x !in (0..lastXIndex) || p.y !in (0..lastYIndex)) return emptySequence()

        return sequence {
            ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(lastYIndex)).forEach { y ->
                ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(lastXIndex)).forEach { x ->
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
        if (lastXIndex == 0 && lastYIndex == 0) return sequenceOf(Point.ZERO)

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
