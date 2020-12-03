package com.github.davio.aoc.general

import kotlin.streams.asSequence

fun getInputList() = ClassLoader.getSystemResourceAsStream("${getCallingClassNumber()}.txt")!!.bufferedReader().readLines()

fun getInputSequenceAsStrings() = getInputList().asSequence()

fun getInputSequenceAsInts() = getInputSequenceAsStrings().map { Integer.parseInt(it) }

fun getInputAsIntList(): List<Int> = getInputList().map { Integer.parseInt(it) }

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