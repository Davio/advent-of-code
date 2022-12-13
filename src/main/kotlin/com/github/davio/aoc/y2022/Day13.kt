package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.split
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

    data class PacketList(val packets: MutableList<Packet> = mutableListOf()) : Packet {
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
        val divider1 = PacketList(mutableListOf(PacketList(mutableListOf(PacketValue(2)))))
        val divider2 = PacketList(mutableListOf(PacketList(mutableListOf(PacketValue(6)))))

        return getInputAsList()
            .split(String::isBlank)
            .map { parsePacketPair(it) }
            .plus(Pair(divider1, divider2))
            .flatMap { listOf(it.first, it.second) }
            .sorted()
            .mapIndexedNotNull { index, packet -> if (packet === divider1 || packet === divider2) index + 1 else null }
            .reduce(Int::times)
    }

    private fun parsePacketPair(packetLines: List<String>): Pair<Packet, Packet> =
        Pair(parsePacket(packetLines[0]), parsePacket(packetLines[1]))

    private val intRegex = Regex("\\d+")

    private fun parsePacket(element: String): Packet {
        if (element.matches(intRegex)) {
            return PacketValue(element.toInt())
        }

        val packetList = PacketList()
        if (element == "[]") return packetList

        val elementSubString = element.substring(1, element.lastIndex)
        val elementsInCurrentList = mutableListOf<String>()
        var currentElement = ""
        var groupLevel = 0

        elementSubString.forEach { c ->
            if (c.isDigit()) currentElement += c
            else if (c == ',') {
                if (groupLevel > 0) {
                    currentElement += ","
                }
                else if (currentElement.isNotBlank()) {
                    elementsInCurrentList.add(currentElement)
                    currentElement = ""
                }
            }
            if (c == '[') {
                currentElement += "["
                groupLevel++
            } else if (c == ']') {
                currentElement += "]"
                groupLevel--
                if (groupLevel == 0) {
                    elementsInCurrentList.add(currentElement)
                    currentElement = ""
                }
            }
        }
        if (currentElement.isNotBlank()) {
            elementsInCurrentList.add(currentElement)
        }
        packetList.packets.addAll(elementsInCurrentList.map { parsePacket(it) })
        return packetList
    }
}
