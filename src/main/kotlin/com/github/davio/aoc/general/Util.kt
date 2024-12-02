package com.github.davio.aoc.general

import kotlinx.coroutines.flow.asFlow

fun Day.getInputReader() = ClassLoader.getSystemResourceAsStream(getCallingClassResourceFile())!!.bufferedReader()

fun Day.getInputAsLine(): String = getInputReader().readLine()

fun Day.getInputAsList(): List<String> = getInputReader().readLines()

fun Day.getInputAsSequence(): Sequence<String> = getInputReader().lineSequence()

fun Day.getInputAsIntSequence() = getInputAsSequence().map { it.toInt() }

fun Day.getInputAsIntList() = getInputAsList().map { it.toInt() }

fun Day.getInputAsLongSequence() = getInputAsSequence().map { it.toLong() }

fun Day.getInputAsLongList() = getInputAsList().map { it.toLong() }

fun Day.getInputAsFlow() = getInputReader().lineSequence().asFlow()

fun <T> Day.getInputAsMatrix(transformer: (Char) -> T): Matrix<T> =
    Matrix(
        getInputAsList().map {
            it.toList().map(transformer)
        },
    )

fun Day.getInputAsMatrix(): Matrix<Char> =
    Matrix(
        getInputAsList().map {
            it.toList()
        },
    )

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
    val mutableList =
        this.fold(mutableListOf<List<T>>()) { acc, element ->
            if (separatorPredicate(element)) {
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
        callingClassName =
            stream
                .filter { stackFrame ->
                    stackFrame.className.contains("Day")
                }.findFirst()
                .map {
                    it.className
                }.orElseThrow()
    }
    var isTest = false
    StackWalker.getInstance().walk { stream ->
        isTest =
            stream.anyMatch { stackFrame ->
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
