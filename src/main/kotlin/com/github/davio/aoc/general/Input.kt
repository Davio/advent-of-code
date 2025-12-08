package com.github.davio.aoc.general

import kotlinx.coroutines.flow.asFlow

fun Day.getInputReader() = ClassLoader.getSystemResourceAsStream(getCallingClassResourceFile())!!.bufferedReader()

fun Day.getInputAsLine(): String = getInputReader().readLine()

fun Day.getInputAsLines(): List<String> = getInputReader().readLines()

fun Day.getInputAsLineSequence(): Sequence<String> = getInputReader().lineSequence()

fun Day.getInputAsIntSequence() = getInputAsLineSequence().map { it.toInt() }

fun Day.getInputAsIntList() = getInputAsLines().map { it.toInt() }

fun Day.getInputAsLongSequence() = getInputAsLineSequence().map { it.toLong() }

fun Day.getInputAsLongList() = getInputAsLines().map { it.toLong() }

fun Day.getInputAsChunks(delimiter: String = System.lineSeparator() + System.lineSeparator()): List<String> {
    val chunks = getInputReader().readAllAsString().split(delimiter).toMutableList()
    if (chunks.isNotEmpty()) {
        chunks[chunks.lastIndex] = chunks.last().removeSuffix(System.lineSeparator())
    }
    return chunks.toList()
}

fun Day.getInputAsFlow() = getInputReader().lineSequence().asFlow()

fun <T> Day.getInputAsMatrix(transformer: (Char) -> T): Matrix<T> =
    Matrix(
        getInputAsLines().map {
            it.toList().map(transformer).toMutableList()
        },
    )

fun Day.getInputAsMatrix(): Matrix<Char> =
    Matrix(
        getInputAsLines().map {
            it.toMutableList()
        },
    )
