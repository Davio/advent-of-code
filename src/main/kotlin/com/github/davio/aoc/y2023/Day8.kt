package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLines
import com.github.davio.aoc.general.lcm

/**
 * See [Advent of Code 2023 Day 8](https://adventofcode.com/2023/day/8#part2])
 */
class Day8(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private val input = getInputAsLines()
    private val directions = input[0].map { it == 'L' }
    private var elementsMap = mutableMapOf<String, Element>()
    private val elements: List<Element>

    init {
        input.drop(2).forEach {
            val id = it.substringBefore(" = ")
            val myElement = elementsMap.getOrPut(id) { Element(id) }
            val (leftId, rightId) = it.substringAfter(" = ").trim('(', ')').split(", ")
            val leftElement = elementsMap.getOrPut(leftId) { Element(leftId) }
            val rightElement = elementsMap.getOrPut(rightId) { Element(rightId) }
            myElement.left = leftElement
            myElement.right = rightElement
        }

        elements = elementsMap.values.filterNot { it == it.left && it.left == it.right }.toList()
    }

    override fun part1(): Long {
        var steps = 0L
        var currentElement = elements.first { it.id == ("AAA") }

        directions.forEach { direction ->
            steps++
            currentElement = if (direction) currentElement.left else currentElement.right

            if (currentElement.id == "ZZZ") {
                return steps
            }
        }

        return -1
    }

    override fun part2(): Long =
        elements
            .filter { it.isStartElement }
            .map {
                getCycleLength(it)
            }.reduce { acc, l ->
                lcm(acc, l)
            }

    private fun getCycleLength(element: Element): Long {
        var stepsToEnd = 0L
        var currentElement = element

        while (!currentElement.isEndElement) {
            stepsToEnd += directions.size
            currentElement =
                directions.fold(currentElement) { acc, d ->
                    if (d) acc.left else acc.right
                }
        }

        return stepsToEnd
    }

    private data class Element(
        val id: String,
    ) {
        val isStartElement = id.endsWith('A')
        val isEndElement = id.endsWith('Z')

        lateinit var left: Element
        lateinit var right: Element

        override fun toString(): String = id
    }
}
