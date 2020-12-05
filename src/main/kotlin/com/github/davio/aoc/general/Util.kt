package com.github.davio.aoc.general

import kotlin.streams.asSequence

fun getInputAsStringList() = getInputReader().readLines()

private fun getInputReader() = ClassLoader.getSystemResourceAsStream("${getCallingClassNumber()}.txt")!!.bufferedReader()

fun getInputAsStringSequence() = getInputReader().lineSequence()

fun getInputAsIntSequence() = getInputAsStringSequence().map { it.toInt() }

fun getInputAsIntList(): List<Int> = getInputAsStringList().map { it.toInt() }

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