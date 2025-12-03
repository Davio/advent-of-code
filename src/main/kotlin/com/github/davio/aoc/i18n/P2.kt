package com.github.davio.aoc.i18n

import com.github.davio.aoc.general.Puzzle
import com.github.davio.aoc.general.getInputAsLines
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.time.format.DateTimeFormatterBuilder

class P2(
    exampleNumber: Int? = null,
) : Puzzle(exampleNumber) {
    private lateinit var timestamps: List<OffsetDateTime>

    private val formatter =
        DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE_TIME)
            .appendOffset("+HH:MM", "+00:00")
            .toFormatter()

    override fun part1(): String =
        timestamps
            .first { t1 ->
                var count = 0
                for (t2 in timestamps) {
                    if (t1.isEqual(t2)) {
                        count++
                        println("$t1 == $t2")
                    }
                    if (count == 4) return@first true
                }
                false
            }.withOffsetSameInstant(ZoneOffset.UTC)
            .format(formatter)

    override fun parseInput() {
        timestamps =
            getInputAsLines().map {
                OffsetDateTime.parse(it)
            }
    }
}
