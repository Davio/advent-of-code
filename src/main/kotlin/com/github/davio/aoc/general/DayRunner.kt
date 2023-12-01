package com.github.davio.aoc.general

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.system.measureTimeMillis

private const val YEAR = 2023
private const val DAY = 1
private const val RUN_PART_1 = false
private const val RUN_PART_2 = true

fun main() {
    val kClass = Class.forName("com.github.davio.aoc.y$YEAR.Day$DAY").kotlin
    if (RUN_PART_1) {
        runPart(kClass, Day::part1, 1)
    }
    if (RUN_PART_2) {
        runPart(kClass, Day::part2, 2)
    }
}

private fun runPart(kClass: KClass<out Any>, partFunction: (Day) -> String, partNumber: Int) {
    val result: String
    measureTimeMillis {
        val day = (if (kClass.objectInstance != null) kClass.objectInstance else kClass.createInstance()) as Day
        result = partFunction.invoke(day)
        val contents = StringSelection(result)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(contents, null)
    }.let {
        println("Part $partNumber answer")
        println("---------------")
        println(result)
        println("---------------")
        println("Took ${it}ms")
        println()
    }
}
