package com.github.davio.aoc.general

import kotlin.streams.asSequence

fun getInputAsList() = getInputReader().readLines()

private fun getInputReader() = ClassLoader.getSystemResourceAsStream("${getCallingClassNumber()}.txt")!!.bufferedReader()

fun getInputSequenceAsStrings() = getInputReader().lineSequence()

fun getInputSequenceAsInts() = getInputSequenceAsStrings().map { it.toInt() }

fun getInputAsIntList(): List<Int> = getInputAsList().map { it.toInt() }

fun getCallingClassNumber(): String {
    val classRegex = Regex(".*P(\\d)")
    return StackWalker.getInstance().walk {
        it.asSequence().first { frame ->
            frame.className.matches(classRegex)
        }!!.run {
            classRegex.matchEntire(this.className)!!.groups[1]!!.value
        }
    }
}