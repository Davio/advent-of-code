package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.*
import kotlin.system.measureTimeMillis

fun main() {
    println(Day8.getResultPart1())
    measureTimeMillis {
        println(Day8.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 8](https://adventofcode.com/2022/day/8#part2])
 */
object Day8 : Day() {

    fun getResultPart1(): Int {
        val grid = parseGrid()
        val visibleTrees = sortedSetOf<Point>()
        (0..grid[0].lastIndex).forEach { x ->
            visibleTrees.add(Point.of(x, 0))
            visibleTrees.add(Point.of(x, grid[0].lastIndex))
        }
        (0..grid.lastIndex).forEach { y ->
            visibleTrees.add(Point.of(0, y))
            visibleTrees.add(Point.of(grid.lastIndex, y))
        }
        (1 until grid.lastIndex).forEach { y ->
            (1 until grid[0].lastIndex).forEach { x ->
                val treeCoords = Point.of(x, y)
                if (!visibleTrees.contains(treeCoords) && isVisible(grid, treeCoords)) {
                    visibleTrees.add(treeCoords)
                }
            }
        }
        return visibleTrees.size
    }

    private fun isVisible(grid: List<List<Int>>, point: Point): Boolean {
        val treeHeight = grid[point.y][point.x]
        return (0 until point.y).all { y -> // From the top
            grid[y][point.x] < treeHeight
        } || (grid.lastIndex downTo point.y + 1).all { y -> // From the bottom
            grid[y][point.x] < treeHeight
        } || (0 until point.x).all { x -> // From the left
            grid[point.y][x] < treeHeight
        } || (grid[0].lastIndex downTo point.x + 1).all { x -> // From the right
            grid[point.y][x] < treeHeight
        }
    }

    private fun getScenicScore(grid: List<List<Int>>, point: Point): Int {
        val treeHeight = grid[point.y][point.x]
        val viewingDistanceTop = (point.y - 1 downTo 0).takeUntil { y -> grid[y][point.x] >= treeHeight }.count()
        val viewingDistanceBottom = (point.y + 1..grid.lastIndex).takeUntil { y -> grid[y][point.x] >= treeHeight }.count()
        val viewingDistanceLeft = (point.x - 1 downTo 0).takeUntil { x -> grid[point.y][x] >= treeHeight }.count()
        val viewingDistanceRight = (point.x + 1..grid[0].lastIndex).takeUntil { x -> grid[point.y][x] >= treeHeight }.count()
        return viewingDistanceTop * viewingDistanceLeft * viewingDistanceBottom * viewingDistanceRight
    }

    fun getResultPart2(): Int {
        val grid = parseGrid()
        return (1 until grid.lastIndex).maxOf { y ->
            (1 until grid[0].lastIndex).maxOf { x ->
                getScenicScore(grid, Point.of(x, y))
            }
        }
    }

    private fun parseGrid() = getInputAsSequence().map {
        parseTreeRow(it)
    }.fold(mutableListOf<List<Int>>()) { acc, row ->
        acc += row
        acc
    }

    private fun parseTreeRow(line: String) = line.toCharArray().map { it.digitToInt() }.toList()
}
