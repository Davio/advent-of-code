package com.github.davio.aoc.i18n

import com.github.davio.aoc.general.Puzzle
import com.github.davio.aoc.general.getInputAsChunks
import com.github.davio.aoc.general.getInputAsLineSequence
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class P4(
    exampleNumber: Int? = null,
) : Puzzle(exampleNumber) {
    private lateinit var trips: List<Trip>

    private val stopRegex = Regex("""(Departure|Arrival):\s+(\p{Alpha}/\p{Alpha})\s+(.+)""")
    private val formatter =
        DateTimeFormatterBuilder()
            .appendText(ChronoField.MONTH_OF_YEAR)
            .toFormatter()

    override fun part1(): Int = getInputAsLineSequence().count { true }

    override fun parseInput() {
        getInputAsChunks().map { chunk ->
            val chunkLines = chunk.lines()
            val departureLine = chunk[0]

            val arrivalLine = chunk[1]
        }
    }

    private fun parseStop(line: String) {
        val (zoneId, formattedTime) = stopRegex.matchEntire(line)!!.destructured
        val timezone = ZoneId.of(zoneId)
        val localTime = LocalTime.parse(formattedTime, DateTimeFormatter.RFC_1123_DATE_TIME)
    }

    data class Trip(
        val departure: Stop,
        val arrival: Stop,
    )

    data class Stop(
        val timezone: ZoneId,
        val localTime: LocalTime,
    )
}
