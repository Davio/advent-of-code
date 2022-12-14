package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.general.split
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlin.system.measureTimeMillis

fun main() {
    println(Day13.getResultPart1())
    measureTimeMillis {
        println(Day13.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 13](https://adventofcode.com/2022/day/13#part2])
 */
object Day13 {

    sealed interface Packet : Comparable<Packet>

    @JvmInline
    value class PacketValue(private val i: Int) : Packet {
        override fun compareTo(other: Packet) =
            if (other is PacketValue) {
                this.i.compareTo(other.i)
            } else {
                PacketList(mutableListOf(this)).compareTo(other)
            }

        override fun toString() = i.toString()
    }

    @JvmInline
    value class PacketList(val packets: List<Packet>) : Packet {
        override fun compareTo(other: Packet): Int {
            if (other is PacketValue) {
                return this.compareTo(PacketList(mutableListOf(other)))
            } else {
                other as PacketList
                this.packets.indices.forEach { index ->
                    if (index > other.packets.lastIndex) {
                        return 1
                    }
                    val comparison = this.packets[index].compareTo(other.packets[index])
                    if (comparison != 0) return comparison
                }
                if (this.packets.size < other.packets.size) {
                    return -1
                }
                return 0
            }
        }

        override fun toString() = "[${packets.joinToString(",")}]"
    }

    fun getResultPart1() =
        getInputAsList()
            .split { it.isBlank() }
            .map { parsePacketPair(it) }
            .withIndex()
            .sumOf { (index, pair) ->
                val left = pair.first
                val right = pair.second
                if (left <= right) index + 1 else 0
            }

    fun getResultPart2(): Int {
        val divider1 = parsePacket("[[2]]")
        val divider2 = parsePacket("[[6]]")

        return getInputAsSequence()
            .filter(String::isNotBlank)
            .map { parsePacket(it) }
            .plus(sequenceOf(divider1, divider2))
            .sorted()
            .mapIndexedNotNull { index, packet -> if (packet == divider1 || packet == divider2) index + 1 else null }
            .reduce(Int::times)
    }

    private fun parsePacketPair(packetLines: List<String>): Pair<Packet, Packet> =
        Pair(parsePacket(packetLines[0]), parsePacket(packetLines[1]))

    private fun parsePacket(line: String) = parseJson(Json.decodeFromString<JsonArray>(line))

    private fun parseJson(jsonElement: JsonElement) : Packet {
        return when (jsonElement) {
            is JsonArray -> PacketList(jsonElement.map { parseJson(it) })
            is JsonPrimitive -> PacketValue(jsonElement.int)
            else -> throw IllegalStateException()
        }
    }
}
