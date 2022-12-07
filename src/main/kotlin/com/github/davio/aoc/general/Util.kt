package com.github.davio.aoc.general

import kotlinx.coroutines.flow.asFlow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun Any.getInputReader() =
    ClassLoader.getSystemResourceAsStream(getCallingClassResourceFile())!!.bufferedReader()

fun Any.getInputAsLine(): String = getInputReader().readLine()

fun Any.getInputAsList() = getInputReader().readLines()

fun Any.getInputAsSequence() = getInputReader().lineSequence()

fun Any.getInputAsIntSequence() = getInputAsSequence().map { it.toInt() }

fun Any.getInputAsIntList() = getInputAsList().map { it.toInt() }

fun Any.getInputAsLongSequence() = getInputAsSequence().map { it.toLong() }

fun Any.getInputAsLongList() = getInputAsList().map { it.toLong() }

fun Any.getInputAsFlow() = getInputReader().lineSequence().asFlow()

fun <T> Sequence<T>.split(separatorPredicate: (T) -> Boolean): Sequence<List<T>> {
    val iterator = this.iterator()
    val buffer = mutableListOf<T>()

    return sequence {
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (separatorPredicate.invoke(element)) {
                yield(buffer.toList())
                buffer.clear()
            } else {
                buffer.add(element)
            }
        }
        if (buffer.isNotEmpty()) {
            yield(buffer.toList())
        }
    }
}

fun <T : Comparable<T>> List<T>.top(n: Int): List<T> = this.sortedDescending().take(n)
fun <T : Comparable<T>> List<T>.bottom(n: Int): List<T> = this.sorted().take(n)
fun <T : Comparable<T>> Sequence<T>.top(n: Int): Sequence<T> = this.sortedDescending().take(n)
fun <T : Comparable<T>> Sequence<T>.bottom(n: Int): Sequence<T> = this.sorted().take(n)

fun Any.getCallingClassResourceFile(): String {
    val clazz = this::class
    val classRegex = Regex("""Day(\d+)""")
    return clazz.qualifiedName!!.split(".").run {
        val (dayNumber) = classRegex.matchEntire(this.last())!!.destructured
        "${this[this.lastIndex - 1]}/${dayNumber}.txt"
    }
}

inline fun <T> T.call(block: (T) -> Unit) {
    block(this)
}

data class Point(var x: Int = 0, var y: Int = 0) {

    operator fun Point.plus(other: Point) = Point(this.x + other.x, this.y + other.y)
    operator fun Point.minus(other: Point) = Point(this.x - other.x, this.y - other.y)

    operator fun plusAssign(point: Point) {
        x += point.x
        y += point.y
    }

    override fun toString(): String {
        return "$x,$y"
    }
}

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

fun gcd(n1: Int, n2: Int): Int {
    return when {
        n1 == 0 -> n2
        n2 == 0 -> n1
        else -> {
            val absNumber1 = abs(n1)
            val absNumber2 = abs(n2)
            val smallerValue = min(absNumber1, absNumber2)
            gcd(max(absNumber1, absNumber2) % smallerValue, smallerValue)
        }
    }
}

fun lcm(n1: Int, n2: Int): Int {
    return if (n1 == 0 || n2 == 0) 0 else {
        val gcd = gcd(n1, n2)
        abs(n1 * n2) / gcd
    }
}

fun <T> List<T>.permutations(k: Int = size): Sequence<List<T>> {
    val newList = ArrayList<T>(this)

    if (k == 1) {
        return sequenceOf(newList)
    }

    fun <T> swap(list: MutableList<T>, index1: Int, index2: Int) {
        val temp = list[index1]
        list[index1] = list[index2]
        list[index2] = temp
    }

    return sequence {
        for (i in (0 until k)) {
            yieldAll(newList.permutations(k - 1))

            if ((k - 1) % 2 == 0) {
                swap(newList, i, k - 1)
            } else {
                swap(newList, 0, k - 1)
            }
        }
    }
}
