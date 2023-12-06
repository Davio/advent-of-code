package com.github.davio.aoc.general

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.time.measureTime

private const val YEAR = 2023
private const val DAY = 6
private const val RUN_PART = 2

fun main() {
    val kClass = Class.forName("com.github.davio.aoc.y$YEAR.Day$DAY").kotlin
    if (RUN_PART == 1) {
        runPart(kClass, Day::part1, 1)
    } else  {
        runPart(kClass, Day::part2, 2)
    }
}

private fun runPart(kClass: KClass<out Any>, partFunction: (Day) -> Any, partNumber: Int) {
    val result: Any
    val day = (if (kClass.objectInstance != null) kClass.objectInstance else kClass.createInstance()) as Day

    val time = measureTime {
        result = partFunction.invoke(day)
    }

    val contents = StringSelection(result.toString())
    Toolkit.getDefaultToolkit().systemClipboard.setContents(contents, null)

    println("Part $partNumber answer")
    println("---------------")
    println(result)
    println("---------------")
    println("Took ${time.inWholeMicroseconds}us")
    println()
}
