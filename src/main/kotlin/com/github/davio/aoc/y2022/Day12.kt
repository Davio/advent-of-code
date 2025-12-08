package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.*
import kotlin.math.floor
import kotlin.system.measureTimeMillis

fun main() {
    println(Day12.getResultPart1())
    measureTimeMillis {
        println(Day12.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 12](https://adventofcode.com/2022/day/12#part2])
 */
object Day12 : Day() {
    fun getResultPart1(): Int {
        val grid = getInputAsMatrix()
        val startingPoint = grid.getPoints().first { p -> grid[p] == 'S' }
        val endPoint = grid.getPoints().first { p -> grid[p] == 'E' }
        val route = aStar(grid, startingPoint, endPoint)
        printRoute(grid, route)
        return route.size - 1
    }

    fun getResultPart2(): Int {
        val knownRoutes = mutableMapOf<Point, List<Point>>()
        val grid = getInputAsMatrix()
        val startingPoints =
            grid.getPoints().filter { p ->
                val charValue = grid[p]
                charValue == 'S' || charValue == 'a'
            }
        val endPoint = grid.getPoints().first { p -> grid[p] == 'E' }
        val shortestRoute =
            startingPoints
                .map {
                    knownRoutes[it] ?: aStar(grid, it, endPoint).apply {
                        drop(1)
                            .filterNot { pointOnRoute -> knownRoutes.containsKey(pointOnRoute) }
                            .forEachIndexed { index, pointOnRoute ->
                                knownRoutes[pointOnRoute] = this.subList(index + 1, this.lastIndex)
                            }
                    }
                }.filter { it.isNotEmpty() }
                .minBy { it.size }
        printRoute(grid, shortestRoute)
        return shortestRoute.size - 1
    }

    private fun printRoute(
        grid: Matrix<Char>,
        route: List<Point>,
    ) {
        val ansiReset = "\u001b[0m"

        val ansiBgColors =
            listOf(
                "\u001b[44m",
                "\u001b[46m",
                "\u001b[42m",
                "\u001b[43m",
                "\u001b[45m",
                "\u001b[41m",
            )

        fun getAnsiBgColor(p: Point): String {
            val height = getCharValue(grid[p]) - 'a'
            return ansiBgColors[floor(height / (26.0 / ansiBgColors.size)).toInt()]
        }

        grid.getPoints().forEach { p ->
            val ansiBgColor = getAnsiBgColor(p)
            print(ansiBgColor)
            if (route.any { it == Point.of(p.x, p.y) }) {
                print("\u001b[30m\u001b[1m${grid[p]}")
            } else {
                print(" ")
            }
            print(ansiReset)
            if (p.x == grid.lastXIndex) {
                println()
            }
        }
    }

    private fun reconstructPath(
        cameFrom: Map<Point, Point>,
        current: Point,
    ): List<Point> {
        val totalPath = mutableListOf(current)
        var myCurrent = current
        while (myCurrent in cameFrom.keys) {
            myCurrent = cameFrom[myCurrent]!!
            totalPath.add(0, myCurrent)
        }

        return totalPath
    }

    private fun heuristicFunction(
        point: Point,
        goal: Point,
    ): Int = point.manhattanDistanceTo(goal)

    private fun aStar(
        grid: Matrix<Char>,
        start: Point,
        goal: Point,
    ): List<Point> {
        // The set of discovered nodes that may need to be (re-)expanded.
        // Initially, only the start node is known.
        // This is usually implemented as a min-heap or priority queue rather than a hash-set.
        val openSet = hashSetOf(start)

        // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start
        // to n currently known.
        val cameFrom = mutableMapOf<Point, Point>()

        // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
        val gScore = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }

        // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
        // how short a path from start to finish can be if it goes through n.
        val fScore = mutableMapOf(start to heuristicFunction(start, goal)).withDefault { Int.MAX_VALUE }

        while (openSet.isNotEmpty()) {
            // This operation can occur in O(1) time if openSet is a min-heap or a priority queue
            val current = openSet.minByOrNull { node -> fScore.getValue(node) }!!
            if (current == goal) {
                return reconstructPath(cameFrom, current)
            }

            openSet.remove(current)
            grid
                .getOrthogonallyAdjacentPoints(current)
                .filter {
                    getCharValue(grid[it]) - getCharValue(grid[current]) <= 1
                }.forEach { neighbor ->
                    // d(current,neighbor) is the weight of the edge from current to neighbor
                    // tentative_gScore is the distance from start to the neighbor through current
                    val dCurrentNeighbor = 1
                    val tentativeGScore = gScore[current]!! + dCurrentNeighbor
                    if (tentativeGScore < gScore.getValue(neighbor)) {
                        // This path to neighbor is better than any previous one. Record it!
                        cameFrom[neighbor] = current
                        gScore[neighbor] = tentativeGScore
                        fScore[neighbor] = tentativeGScore + heuristicFunction(neighbor, goal)
                        openSet.add(neighbor)
                    }
                }
        }

        return emptyList()
    }

    private fun getCharValue(c: Char) =
        when (c) {
            'S' -> 'a'
            'E' -> 'z'
            else -> c
        }
}
