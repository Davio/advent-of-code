package com.github.davio.aoc.general

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.time.temporal.ChronoUnit
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.toJavaDuration
import kotlin.time.toKotlinDuration

private const val TYPE = "y2025"
private const val PREFIX = "Day"
private const val INSTANCE = 4
private const val PART = 2
private const val COPY_TO_CLIPBOARD = true

fun main() {
    val kClass = Class.forName("com.github.davio.aoc.$TYPE.$PREFIX$INSTANCE").kotlin
    if (PART == 1) {
        runPart(kClass, Day::part1, 1)
    } else {
        runPart(kClass, Day::part2, 2)
    }
}

@OptIn(ExperimentalTime::class)
private fun runPart(
    kClass: KClass<out Any>,
    partFunction: (Day) -> Any,
    partNumber: Int,
) {
    val result: Any
    val day = (if (kClass.objectInstance != null) kClass.objectInstance else kClass.createInstance()) as Day

    val time =
        measureTime {
            result = partFunction.invoke(day)
        }.toJavaDuration().truncatedTo(ChronoUnit.MILLIS).toKotlinDuration()

    if (COPY_TO_CLIPBOARD) {
        val contents = StringSelection(result.toString())
        Toolkit.getDefaultToolkit().systemClipboard.setContents(contents, null)
    }

    println("Part $partNumber answer")
    println("---------------")
    println(result)
    println("---------------")
    println("Took $time")
    println()
}
