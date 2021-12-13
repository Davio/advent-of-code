package com.github.davio.aoc.y2021

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import kotlin.system.measureTimeMillis

fun main() {
    Day11.getResultPart1()
    measureTimeMillis {
        Day11.getResultPart2()
    }.call { println("Took $it ms") }
}

object Day11 {

    /*
    --- Day 11: Dumbo Octopus ---

You enter a large cavern full of rare bioluminescent dumbo octopuses! They seem to not like the Christmas lights on your submarine, so you turn them off for now.

There are 100 octopuses arranged neatly in a 10 by 10 grid. Each octopus slowly gains energy over time and flashes brightly for a moment when its energy is full. Although your lights are off, maybe you could navigate through the cave without disturbing the octopuses if you could predict when the flashes of light will happen.

Each octopus has an energy level - your submarine can remotely measure the energy level of each octopus (your puzzle input). For example:

5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526

The energy level of each octopus is a value between 0 and 9. Here, the top-left octopus has an energy level of 5, the bottom-right one has an energy level of 6, and so on.

You can model the energy levels and flashes of light in steps. During a single step, the following occurs:

    First, the energy level of each octopus increases by 1.
    Then, any octopus with an energy level greater than 9 flashes. This increases the energy level of all adjacent octopuses by 1, including octopuses that are diagonally adjacent. If this causes an octopus to have an energy level greater than 9, it also flashes. This process continues as long as new octopuses keep having their energy level increased beyond 9. (An octopus can only flash at most once per step.)
    Finally, any octopus that flashed during this step has its energy level set to 0, as it used all of its energy to flash.

Adjacent flashes can cause an octopus to flash on a step even if it begins that step with very little energy. Consider the middle octopus with 1 energy in this situation:

Before any steps:
11111
19991
19191
19991
11111

After step 1:
34543
40004
50005
40004
34543

After step 2:
45654
51115
61116
51115
45654

An octopus is highlighted when it flashed during the given step.

After 100 steps, there have been a total of 1656 flashes.

Given the starting energy levels of the dumbo octopuses in your cavern, simulate 100 steps. How many total flashes are there after 100 steps?
    */

    private var grid: Array<Array<Octopus>> = emptyArray()

    private const val boldRedColor = "\u001B[31m\u001B[1m"
    private const val ansiReset = "\u001B[0m"

    private var flashes = 0

    fun getResultPart1() {
        grid = getOctopusGrid()

        val steps = 100

        println("Before any steps:")
        printGrid()

        (1..steps).forEach { step ->
            doStep(step)
        }

        println(flashes)
    }

    private fun doStep(step: Int) {
        (grid.indices).forEach { y ->
            (grid.indices).forEach { x ->
                val octopus = grid[y][x]
                octopus.flashed = false
                octopus.energyLevel++
            }
        }

        println("Before flashing after step $step:")
        printGrid()

        (grid.indices).forEach { y ->
            (grid.indices).filter { x ->
                grid[y][x].energyLevel > 9
            }.forEach { x ->
                flash(grid[y][x])
            }
        }

        println("After step $step:")
        printGrid()
    }

    private fun flash(octopus: Octopus) {
        octopus.energyLevel = 0
        octopus.flashed = true
        flashes++

        getNeighbors(octopus).filter {
            !it.flashed && it.energyLevel <= 9
        }.forEach { neighbor ->
            neighbor.energyLevel++
            if (neighbor.energyLevel > 9) {
                flash(neighbor)
            }
        }
    }

    private fun printGrid() {
        (grid.indices).forEach { y ->
            (grid.indices).forEach { x ->
                val energyLevel = grid[y][x].energyLevel
                val color = if (energyLevel == 0 || energyLevel > 9) boldRedColor else ansiReset
                val energyOutput = if (energyLevel > 9) 'X' else energyLevel.digitToChar()
                print("$color$energyOutput")
            }
            println()
        }
        println(ansiReset)
    }

    /*
    --- Part Two ---

It seems like the individual flashes aren't bright enough to navigate. However, you might have a better option: the flashes seem to be synchronizing!

In the example above, the first time all octopuses flash simultaneously is step 195:

After step 193:
5877777777
8877777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777

After step 194:
6988888888
9988888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888

After step 195:
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000

If you can calculate the exact moments when the octopuses will all flash simultaneously, you should be able to navigate through the cavern. What is the first step during which all octopuses flash?
    */

    fun getResultPart2() {
        grid = getOctopusGrid()
        var step = 0

        println("Before any steps:")
        printGrid()

        while(!gridIsSynchronized()) {
            doStep(++step)
        }

        println(step)
    }

    private fun gridIsSynchronized(): Boolean {
        return grid.all { row ->
            row.all { it.energyLevel == 0}
        }
    }

    private fun getNeighbors(octopus: Octopus): Sequence<Octopus> {
        val x = octopus.x
        val y = octopus.y

        return (y - 1..y + 1).asSequence().flatMap { yNeighbor ->
            (x - 1..x + 1).asSequence().filter { xNeighbor ->
                (yNeighbor != y || xNeighbor != x)
                        && yNeighbor in grid.indices
                        && xNeighbor in grid[0].indices
            }.map { xNeighbor ->
                grid[yNeighbor][xNeighbor]
            }
        }
    }

    private fun getOctopusGrid(): Array<Array<Octopus>> {
        return getInputAsList().mapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, energyLevel ->
                Octopus(x, y, energyLevel.digitToInt())
            }.toTypedArray()
        }.toTypedArray()
    }

    private data class Octopus(val x: Int, val y: Int, var energyLevel: Int, var flashed: Boolean = false)
}