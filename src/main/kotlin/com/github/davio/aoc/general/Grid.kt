package com.github.davio.aoc.general

typealias Grid<T> = Array<Array<T>>
typealias CharGrid = Array<CharArray>

private fun getAdjacentPoints(p: Point, maxX: Int, maxY: Int): Sequence<Point> {
    if (maxX == 0 && maxY == 0) return emptySequence()
    return sequence {
        ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(maxY)).forEach { y ->
            ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(maxX)).forEach { x ->
                if (y != p.y || x != p.x) {
                    yield(Point(x, y))
                }
            }
        }
    }
}

private fun getOrthogonallyAdjacentPoints(p: Point, maxX: Int, maxY: Int): Sequence<Point> {
    if (maxX == 0 && maxY == 0) return emptySequence()
    return sequence {
        ((p.y - 1).coerceAtLeast(0)..(p.y + 1).coerceAtMost(maxY)).forEach { y ->
            ((p.x - 1).coerceAtLeast(0)..(p.x + 1).coerceAtMost(maxX)).forEach { x ->
                if ((y != p.y || x != p.x) && (y == p.y || x == p.x)) {
                    yield(Point(x, y))
                }
            }
        }
    }
}

private fun getPoints(maxX: Int, maxY: Int): Sequence<Point> {
    if (maxX == 0 && maxY == 0) return sequenceOf(Point.ZERO)
    return sequence {
        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                yield(Point(x, y))
            }
        }
    }
}

fun <T> Grid<T>.getValue(p: Point): T = this[p.y][p.x]
fun <T> Grid<T>.getPoints(): Sequence<Point> = getPoints(this[0].lastIndex, this.lastIndex)
fun <T> Grid<T>.getAdjacentPoints(p: Point): Sequence<Point> = getAdjacentPoints(p, this[0].lastIndex, this.lastIndex)
fun <T> Grid<T>.getOrthogonallyAdjacentPoints(p: Point) = getOrthogonallyAdjacentPoints(p, this[0].lastIndex, this.lastIndex)
fun <T> Grid<T>.getAdjacentValues(p: Point): Sequence<T> = this.getAdjacentPoints(p).map { this[it.y][it.x] }
fun <T> Grid<T>.getOrthogonallyAdjacentValues(p: Point): Sequence<T> = this.getOrthogonallyAdjacentPoints(p).map { this[it.y][it.x] }

fun CharGrid.getValue(p: Point): Char = this[p.y][p.x]
fun CharGrid.getPoints(): Sequence<Point> = getPoints(this[0].lastIndex, this.lastIndex)
fun CharGrid.getAdjacentPoints(p: Point): Sequence<Point> = getAdjacentPoints(p, this[0].lastIndex, this.lastIndex)
fun CharGrid.getOrthogonallyAdjacentPoints(p: Point) = getOrthogonallyAdjacentPoints(p, this[0].lastIndex, this.lastIndex)
fun CharGrid.getAdjacentValues(p: Point): Sequence<Char> = this.getAdjacentPoints(p).map { this[it.y][it.x] }
fun CharGrid.getOrthogonallyAdjacentValues(p: Point): Sequence<Char> = this.getOrthogonallyAdjacentPoints(p).map { this[it.y][it.x] }

fun <T> Grid<T>.asString() = this.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = "") }
fun CharGrid.asString() = this.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = "") }
