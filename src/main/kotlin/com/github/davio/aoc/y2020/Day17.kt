package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsLines
import java.lang.System.lineSeparator
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        Day17.getResult()
    }.call { println("$it ms") }
}

object Day17 : Day() {
    /*
     * --- Day 17: Conway Cubes ---

As your flight slowly drifts through the sky, the Elves at the Mythical Information Bureau at the North Pole contact you.
* They'd like some help debugging a malfunctioning experimental energy source aboard one of their super-secret imaging satellites.

The experimental energy source is based on cutting-edge technology: a set of Conway Cubes contained in a pocket dimension!
* When you hear it's having problems, you can't help but agree to take a look.

The pocket dimension contains an infinite 3-dimensional grid. At every integer 3-dimensional coordinate (x,y,z),
* there exists a single cube which is either active or inactive.

In the initial state of the pocket dimension, almost all cubes start inactive.
* The only exception to this is a small flat region of cubes (your puzzle input);
* the cubes in this region start in the specified active (#) or inactive (.) state.

The energy source then proceeds to boot up by executing six cycles.

Each cube only ever considers its neighbors: any of the 26 other cubes where any of their coordinates differ by at most 1.
* For example, given the cube at x=1,y=2,z=3, its neighbors include the cube at x=2,y=2,z=2, the cube at x=0,y=2,z=3, and so on.

During a cycle, all cubes simultaneously change their state according to the following rules:

    If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
    If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.

The engineers responsible for this experimental energy source would like you to simulate the pocket dimension and determine
* what the configuration of cubes should be at the end of the six-cycle boot process.

For example, consider the following initial state:

.#.
..#
###

Even though the pocket dimension is 3-dimensional, this initial state represents a small 2-dimensional slice of it.
* (In particular, this initial state defines a 3x3x1 region of the 3-dimensional space.)

Simulating a few cycles from this initial state produces the following configurations,
* where the result of each cycle is shown layer-by-layer at each given z coordinate
* (and the frame of view follows the active cells in each cycle):

Before any cycles:

z=0
.#.
..#
###


After 1 cycle:

z=-1
#..
..#
.#.

z=0
#.#
.##
.#.

z=1
#..
..#
.#.


After 2 cycles:

z=-2
.....
.....
..#..
.....
.....

z=-1
..#..
.#..#
....#
.#...
.....

z=0
##...
##...
#....
....#
.###.

z=1
..#..
.#..#
....#
.#...
.....

z=2
.....
.....
..#..
.....
.....


After 3 cycles:

z=-2
.......
.......
..##...
..###..
.......
.......
.......

z=-1
..#....
...#...
#......
.....##
.#...#.
..#.#..
...#...

z=0
...#...
.......
#......
.......
.....##
.##.#..
...#...

z=1
..#....
...#...
#......
.....##
.#...#.
..#.#..
...#...

z=2
.......
.......
..##...
..###..
.......
.......
.......

After the full six-cycle boot process completes, 112 cubes are left in the active state.

Starting with your given initial configuration, simulate six cycles. How many cubes are left in the active state after the sixth cycle?
*
* --- Part Two ---

For some reason, your simulated results don't match what the experimental energy source engineers expected.
* Apparently, the pocket dimension actually has four spatial dimensions, not three.

The pocket dimension contains an infinite 4-dimensional grid. At every integer 4-dimensional coordinate (x,y,z,w),
* there exists a single cube (really, a hypercube) which is still either active or inactive.

Each cube only ever considers its neighbors: any of the 80 other cubes where any of their coordinates differ by at most 1.
* For example, given the cube at x=1,y=2,z=3,w=4, its neighbors include the cube at x=2,y=2,z=3,w=3, the cube at x=0,y=2,z=3,w=4, and so on.

The initial state of the pocket dimension still consists of a small flat region of cubes.
* Furthermore, the same rules for cycle updating still apply: during each cycle, consider the number of active neighbors of each cube.

For example, consider the same initial state as in the example above. Even though the pocket dimension is 4-dimensional,
* this initial state represents a small 2-dimensional slice of it.
* (In particular, this initial state defines a 3x3x1x1 region of the 4-dimensional space.)

Simulating a few cycles from this initial state produces the following configurations,
* where the result of each cycle is shown layer-by-layer at each given z and w coordinate:

Before any cycles:

z=0, w=0
.#.
..#
###


After 1 cycle:

z=-1, w=-1
#..
..#
.#.

z=0, w=-1
#..
..#
.#.

z=1, w=-1
#..
..#
.#.

z=-1, w=0
#..
..#
.#.

z=0, w=0
#.#
.##
.#.

z=1, w=0
#..
..#
.#.

z=-1, w=1
#..
..#
.#.

z=0, w=1
#..
..#
.#.

z=1, w=1
#..
..#
.#.


After 2 cycles:

z=-2, w=-2
.....
.....
..#..
.....
.....

z=-1, w=-2
.....
.....
.....
.....
.....

z=0, w=-2
###..
##.##
#...#
.#..#
.###.

z=1, w=-2
.....
.....
.....
.....
.....

z=2, w=-2
.....
.....
..#..
.....
.....

z=-2, w=-1
.....
.....
.....
.....
.....

z=-1, w=-1
.....
.....
.....
.....
.....

z=0, w=-1
.....
.....
.....
.....
.....

z=1, w=-1
.....
.....
.....
.....
.....

z=2, w=-1
.....
.....
.....
.....
.....

z=-2, w=0
###..
##.##
#...#
.#..#
.###.

z=-1, w=0
.....
.....
.....
.....
.....

z=0, w=0
.....
.....
.....
.....
.....

z=1, w=0
.....
.....
.....
.....
.....

z=2, w=0
###..
##.##
#...#
.#..#
.###.

z=-2, w=1
.....
.....
.....
.....
.....

z=-1, w=1
.....
.....
.....
.....
.....

z=0, w=1
.....
.....
.....
.....
.....

z=1, w=1
.....
.....
.....
.....
.....

z=2, w=1
.....
.....
.....
.....
.....

z=-2, w=2
.....
.....
..#..
.....
.....

z=-1, w=2
.....
.....
.....
.....
.....

z=0, w=2
###..
##.##
#...#
.#..#
.###.

z=1, w=2
.....
.....
.....
.....
.....

z=2, w=2
.....
.....
..#..
.....
.....

After the full six-cycle boot process completes, 848 cubes are left in the active state.

Starting with your given initial configuration, simulate six cycles in a 4-dimensional space.
* How many cubes are left in the active state after the sixth cycle?
     */

    private val input = getInputAsLines()

    private val cubes = hashMapOf<Point4D, Boolean>()
    private val cubesToChange = hashMapOf<Point4D, Boolean>()

    private var minX: Int = 0
    private var maxX: Int = 0
    private var minY: Int = 0
    private var maxY: Int = 0
    private var minZ: Int = 0
    private var maxZ: Int = 0
    private var minW: Int = 0
    private var maxW: Int = 0

    fun getResult() {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, state ->
                cubes[Point4D(x, y, 0, 0)] = state == '#'
            }
        }

        maxX = cubes.keys.maxOf { it.x }
        maxY = cubes.keys.maxOf { it.y }

        println(this)
        repeat(6) {
            simulateCycle()
            println("After ${it + 1} cycle${if (it > 0) "s" else ""}:")
            println(this)
        }

        println(cubes.count { it.value })
    }

    private fun simulateCycle() {
        minY--
        maxY++
        minZ--
        maxZ++
        minW--
        maxW++

        (--minX..++maxX)
            .flatMap { x ->
                (minY..maxY).flatMap { y ->
                    (minZ..maxZ).flatMap { z ->
                        (minY..maxY).map { w ->
                            Point4D(x, y, z, w)
                        }
                    }
                }
            }.forEach {
                if (!cubes.containsKey(it)) {
                    cubes[it] = false
                }

                val numberOfActiveNeighbors = getNumberOfActiveNeighbors(it)
                if (cubes[it]!! && numberOfActiveNeighbors !in (2..3)) {
                    cubesToChange[it] = false
                } else if (!cubes[it]!! && numberOfActiveNeighbors == 3) {
                    cubesToChange[it] = true
                }
            }

        cubesToChange.forEach { cubes[it.key] = it.value }
    }

    private fun getNeighbors(point: Point4D): List<Point4D> =
        ((point.x - 1)..(point.x + 1)).flatMap { x ->
            ((point.y - 1)..(point.y + 1)).flatMap { y ->
                ((point.z - 1)..(point.z + 1)).flatMap { z ->
                    ((point.w - 1)..(point.w + 1))
                        .filterNot { w ->
                            x == point.x && y == point.y && z == point.z && w == point.w
                        }.map { w ->
                            val neighbor = Point4D(x, y, z, w)
                            neighbor
                        }
                }
            }
        }

    private fun getNumberOfActiveNeighbors(point: Point4D): Int =
        getNeighbors(point).count {
            cubes.getOrDefault(it, false)
        }

    private data class Point4D(
        val x: Int,
        val y: Int,
        val z: Int,
        val w: Int,
    )

    override fun toString(): String {
        var result = ""
        val activeCubes = cubes.keys.filter { cubes[it]!! }
        val minX = activeCubes.minOf { it.x }
        val maxX = activeCubes.maxOf { it.x }
        val minY = activeCubes.minOf { it.y }
        val maxY = activeCubes.maxOf { it.y }
        val minZ = activeCubes.minOf { it.z }
        val maxZ = activeCubes.maxOf { it.z }
        val minW = activeCubes.minOf { it.w }
        val maxW = activeCubes.maxOf { it.w }

        (minW..maxW).forEach { w ->
            result += lineSeparator() + "w=$w" + lineSeparator()
            (minZ..maxZ).forEach { z ->
                result += lineSeparator() + "z=$z" + lineSeparator()
                (minY..maxY).forEach { y ->
                    (minX..maxX).forEach { x ->
                        result += if (cubes[Point4D(x, y, z, w)]!!) '#' else '.'
                    }
                    result += lineSeparator()
                }
            }
        }

        return result
    }
}
