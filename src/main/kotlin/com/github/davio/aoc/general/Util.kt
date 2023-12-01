package com.github.davio.aoc.general

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun Day.getInputReader() =
    ClassLoader.getSystemResourceAsStream(getCallingClassResourceFile())!!.bufferedReader()

fun Day.getInputAsLine(): String = getInputReader().readLine()

fun Day.getInputAsList() = getInputReader().readLines()

fun Day.getInputAsSequence() = getInputReader().lineSequence()

fun Day.getInputAsIntSequence() = getInputAsSequence().map { it.toInt() }

fun Day.getInputAsIntList() = getInputAsList().map { it.toInt() }

fun Day.getInputAsLongSequence() = getInputAsSequence().map { it.toLong() }

fun Day.getInputAsLongList() = getInputAsList().map { it.toLong() }

fun Day.getInputAsFlow() = getInputReader().lineSequence().asFlow()

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
            buffer.clear()
        }
    }
}

fun <T> List<T>.split(separatorPredicate: (T) -> Boolean): List<List<T>> {
    val buffer = mutableListOf<T>()
    val mutableList = this.fold(mutableListOf<List<T>>()) { acc, element ->
        if(separatorPredicate.invoke(element)) {
            acc.add(buffer.toList())
            buffer.clear()
        } else {
            buffer.add(element)
        }
        acc
    }
    if (buffer.isNotEmpty()) {
        mutableList.add(buffer.toList())
        buffer.clear()
    }
    return mutableList.toList()
}

fun <T : Comparable<T>> Iterable<T>.top(n: Int): Iterable<T> = this.sortedDescending().take(n)
fun <T : Comparable<T>> Iterable<T>.bottom(n: Int): Iterable<T> = this.sorted().take(n)
fun <T : Comparable<T>> Sequence<T>.top(n: Int): Sequence<T> = this.sortedDescending().take(n)
fun <T : Comparable<T>> Sequence<T>.bottom(n: Int): Sequence<T> = this.sorted().take(n)

fun Day.getCallingClassResourceFile(): String {
    var callingClassName = ""
    StackWalker.getInstance().walk { stream ->
        callingClassName = stream.filter { stackFrame ->
            stackFrame.className.contains("Day")
        }.findFirst().map { it.className }.orElseThrow()
    }
    var isTest = false
    StackWalker.getInstance().walk { stream ->
        isTest = stream.anyMatch { stackFrame ->
            stackFrame.className.matches(Regex(""".*Day\d+Test.*"""))
        }
    }
    val classRegex = Regex("""Day(\d+)""")
    val (dayNumber) = classRegex.matchEntire(callingClassName.substringAfterLast("."))!!.destructured
    val folder = callingClassName.substringBeforeLast(".").replace('.', '/') + "/day$dayNumber"
    val fileName = if (!isTest) "input" else "example" + (exampleNumber?.let { "-$it" } ?: "")
    return "$folder/$fileName.txt"
}

inline fun <T> T.call(block: (T) -> Unit) {
    block(this)
}

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        if (predicate(item)) {
            list.add(item)
            break
        }
        list.add(item)
    }
    return list
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

fun gcd(n1: Long, n2: Long):Long {
    return when {
        n1 == 0L -> n2
        n2 == 0L -> n1
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

fun lcm(n1: Long, n2: Long): Long {
    return if (n1 == 0L || n2 == 0L) 0L else {
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
