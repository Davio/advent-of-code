package com.github.davio.aoc.general

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.streams.asSequence

fun getInputAsList() = getInputReader().readLines()

private fun getInputReader() = ClassLoader.getSystemResourceAsStream(getCallingClassResourceFile())!!.bufferedReader()

fun getInputAsSequence() = getInputReader().lineSequence()

fun getInputAsIntSequence() = getInputAsSequence().map { it.toInt() }

fun getInputAsIntList() = getInputAsList().map { it.toInt() }

fun getInputAsLongSequence() = getInputAsSequence().map { it.toLong() }

fun getInputAsLongList() = getInputAsList().map { it.toLong() }

fun getCallingClassResourceFile(): String {
    val classRegex = Regex(""".*(y\d{4})\.Day(\d+)""")
    return StackWalker.getInstance().walk {
        it.asSequence().first { frame ->
            println(frame.className)
            frame.className.matches(classRegex)
        }!!.run {
            val matchResult = classRegex.matchEntire(this.className)!!
            val year = matchResult.groups[1]!!.value
            val dayNumber = matchResult.groups[2]!!.value
            "$year/$dayNumber.txt"
        }
    }
}

inline fun <T> T.call(codeBlock: (T) -> Unit) {
    codeBlock(this)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(this.first + other.first, this.second + other.second)

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

fun <T> permutate(list: List<T> = emptyList(), k: Int = list.size): Sequence<List<T>> {
    val newList = ArrayList<T>(list)

    if (k == 1) {
        return sequenceOf(newList)
    }

    return sequence {
        for (i in (0 until k)) {
            yieldAll(permutate(newList, k - 1))

            if ((k - 1) % 2 == 0) {
                swap(newList, i, k - 1)
            } else {
                swap(newList, 0, k - 1)
            }
        }
    }
}

private fun <T> swap(list: MutableList<T>, index1: Int, index2: Int) {
    val temp = list[index1]
    list[index1] = list[index2]
    list[index2] = temp
}