package com.github.davio.aoc.y2021

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsLines
import kotlin.system.measureTimeMillis

fun main() {
    Day9.getResultPart1()
    measureTimeMillis {
        Day9.getResultPart2()
    }.call { println("Took $it ms") }
}

object Day9 : Day() {
    /*
    --- Day 9: Smoke Basin ---

These caves seem to be lava tubes. Parts are even still volcanically active; small hydrothermal vents release smoke into the caves that slowly settles like rain.

If you can model how the smoke flows through the caves, you might be able to avoid it and be that much safer. The submarine generates a heightmap of the floor of the nearby caves for you (your puzzle input).

Smoke flows to the lowest point of the area it's in. For example, consider the following heightmap:

2199943210
3987894921
9856789892
8767896789
9899965678

Each number corresponds to the height of a particular location, where 9 is the highest and 0 is the lowest a location can be.

Your first goal is to find the low points - the locations that are lower than any of its adjacent locations. Most locations have four adjacent locations (up, down, left, and right); locations on the edge or corner of the map have three or two adjacent locations, respectively. (Diagonal locations do not count as adjacent.)

In the above example, there are four low points, all highlighted: two are in the first row (a 1 and a 0), one is in the third row (a 5), and one is in the bottom row (also a 5). All other locations on the heightmap have some lower adjacent location, and so are not low points.

The risk level of a low point is 1 plus its height. In the above example, the risk levels of the low points are 2, 1, 6, and 6. The sum of the risk levels of all low points in the heightmap is therefore 15.

Find all of the low points on your heightmap. What is the sum of the risk levels of all low points on your heightmap?
     */

    private var cave: Array<IntArray> = arrayOf()

    private const val boldRedColor = "\u001B[31m\u001B[1m"
    private const val ansiReset = "\u001B[0m"

    fun getResultPart1() {
        cave = getCave()
        val lowPoints =
            (cave.indices).flatMap { y ->
                val result =
                    (cave[0].indices)
                        .filter { x ->
                            val row = cave[y]
                            val height = row[x]
                            if (height == 9 || !isMinimumHeight(height, x, y)) {
                                print("$ansiReset$height")
                                false
                            } else {
                                print("$boldRedColor$height")
                                true
                            }
                        }.map { x ->
                            cave[y][x]
                        }
                println()
                result
            }
        println(lowPoints)
        println(lowPoints.sum() + lowPoints.count())
    }

    private fun isMinimumHeight(
        height: Int,
        x: Int,
        y: Int,
    ): Boolean = getNeighborHeights(x, y).all { height < it }

    private fun getNeighborHeights(
        x: Int,
        y: Int,
    ): Sequence<Int> =
        (y - 1..y + 1)
            .flatMap { yNeighbor ->
                (x - 1..x + 1)
                    .filter { xNeighbor ->
                        (yNeighbor == y || xNeighbor == x) &&
                            !(yNeighbor == y && xNeighbor == x) &&
                            yNeighbor in (cave.indices) &&
                            xNeighbor in (cave[0].indices)
                    }.map { xNeighbor ->
                        cave[yNeighbor][xNeighbor]
                    }
            }.asSequence()

    /*
    --- Part Two ---

Next, you need to find the largest basins so you know what areas are most important to avoid.

A basin is all locations that eventually flow downward to a single low point. Therefore, every low point has a basin, although some basins are very small. Locations of height 9 do not count as being in any basin, and all other locations will always be part of exactly one basin.

The size of a basin is the number of locations within the basin, including the low point. The example above has four basins.

The top-left basin, size 3:

2199943210
3987894921
9856789892
8767896789
9899965678

The top-right basin, size 9:

2199943210
3987894921
9856789892
8767896789
9899965678

The middle basin, size 14:

2199943210
3987894921
9856789892
8767896789
9899965678

The bottom-right basin, size 9:

2199943210
3987894921
9856789892
8767896789
9899965678

Find the three largest basins and multiply their sizes together. In the above example, this is 9 * 14 * 9 = 1134.

What do you get if you multiply together the sizes of the three largest basins?
     */

    private var basinIndex = -1
    private val basinSizes: MutableList<Int> = mutableListOf()
    private val pointsInBasins: MutableSet<Pair<Int, Int>> = mutableSetOf()

    fun getResultPart2() {
        cave = getCave()
        (cave.indices).forEach { y ->
            (cave[0].indices)
                .asSequence()
                .filter { x ->
                    cave[y][x] != 9 && !pointsInBasins.contains(Pair(x, y))
                }.forEach { x ->
                    addBasin(x, y)
                }
        }
        val largestThreeBasinSizesMultiplied =
            basinSizes
                .sortedDescending()
                .take(3)
                .also { println(it) }
                .reduce { left, right -> left * right }
        println(largestThreeBasinSizesMultiplied)
    }

    private fun addBasin(
        x: Int,
        y: Int,
    ) {
        basinSizes.add(1)
        basinIndex++
        pointsInBasins.add(Pair(x, y))
        addBasinNeighbors(x, y)
    }

    private fun addBasinNeighbors(
        x: Int,
        y: Int,
    ) {
        (y - 1..y + 1).forEach { yNeighbor ->
            (x - 1..x + 1)
                .asSequence()
                .filter { xNeighbor ->
                    (yNeighbor == y || xNeighbor == x) &&
                        !(yNeighbor == y && xNeighbor == x) &&
                        yNeighbor in (cave.indices) &&
                        xNeighbor in (cave[0].indices) &&
                        cave[yNeighbor][xNeighbor] != 9 &&
                        !pointsInBasins.contains(Pair(xNeighbor, yNeighbor))
                }.forEach { xNeighbor ->
                    pointsInBasins.add(Pair(xNeighbor, yNeighbor))
                    addBasinNeighbors(xNeighbor, yNeighbor)
                    basinSizes[basinIndex]++
                }
        }
    }

    private fun getCave(): Array<IntArray> =
        getInputAsLines()
            .map { line ->
                line.toCharArray().map { it.digitToInt() }.toIntArray()
            }.toTypedArray()
}
