package com.github.davio.aoc.general

data class Matrix<T>(
    val data: List<List<T>>,
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

    operator fun get(
        x: Int,
        y: Int,
    ): T = data[ y][x]

    operator fun get(p: Point): T = data[p.y][p.x]

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

    override fun toString(): String = data.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = " ") }
}
