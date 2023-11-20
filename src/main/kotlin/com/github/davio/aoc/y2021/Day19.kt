package com.github.davio.aoc.y2021

import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import com.github.davio.aoc.y2021.Day19.getResultPart1
import com.github.davio.aoc.y2021.Day19.getResultPart2
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    getResultPart1()
    measureTimeMillis {
        getResultPart2()
    }.call { println("Took $it ms") }
}

object Day19 {

    /*
    --- Day 19: Beacon Scanner ---

As your probe drifted down through this area, it released an assortment of beacons and scanners into the water. It's difficult to navigate in the pitch black open waters of the ocean trench, but if you can build a map of the trench using data from the scanners, you should be able to safely reach the bottom.

The beacons and scanners float motionless in the water; they're designed to maintain the same position for long periods of time. Each scanner is capable of detecting all beacons in a large cube centered on the scanner; beacons that are at most 1000 units away from the scanner in each of the three axes (x, y, and z) have their precise position determined relative to the scanner. However, scanners cannot detect other scanners. The submarine has automatically summarized the relative positions of beacons detected by each scanner (your puzzle input).

For example, if a scanner is at x,y,z coordinates 500,0,-500 and there are beacons at -500,1000,-1500 and 1501,0,-500, the scanner could report that the first beacon is at -1000,1000,-1000 (relative to the scanner) but would not detect the second beacon at all.

Unfortunately, while each scanner can report the positions of all detected beacons relative to itself, the scanners do not know their own position. You'll need to determine the positions of the beacons and scanners yourself.

The scanners and beacons map a single contiguous 3d region. This region can be reconstructed by finding pairs of scanners that have overlapping detection regions such that there are at least 12 beacons that both scanners detect within the overlap. By establishing 12 common beacons, you can precisely determine where the scanners are relative to each other, allowing you to reconstruct the beacon map one scanner at a time.

For a moment, consider only two dimensions. Suppose you have the following scanner reports:

--- scanner 0 ---
0,2
4,1
3,3

--- scanner 1 ---
-1,-1
-5,0
-2,1

Drawing x increasing rightward, y increasing upward, scanners as S, and beacons as B, scanner 0 detects this:

...B.
B....
....B
S....

Scanner 1 detects this:

...B..
B....S
....B.

For this example, assume scanners only need 3 overlapping beacons. Then, the beacons visible to both scanners overlap to produce the following complete map:

...B..
B....S
....B.
S.....

Unfortunately, there's a second problem: the scanners also don't know their rotation or facing direction. Due to magnetic alignment, each scanner is rotated some integer number of 90-degree turns around all of the x, y, and z axes. That is, one scanner might call a direction positive x, while another scanner might call that direction negative y. Or, two scanners might agree on which direction is positive x, but one scanner might be upside-down from the perspective of the other scanner. In total, each scanner could be in any of 24 different orientations: facing positive or negative x, y, or z, and considering any of four directions "up" from that facing.

For example, here is an arrangement of beacons as seen from a scanner in the same position but in different orientations:

--- scanner 0 ---
-1,-1,1
-2,-2,2
-3,-3,3
-2,-3,1
5,6,-4
8,0,7

--- scanner 0 ---
1,-1,1
2,-2,2
3,-3,3
2,-1,3
-5,4,-6
-8,-7,0

--- scanner 0 ---
-1,-1,-1
-2,-2,-2
-3,-3,-3
-1,-3,-2
4,6,5
-7,0,8

--- scanner 0 ---
1,1,-1
2,2,-2
3,3,-3
1,3,-2
-4,-6,5
7,0,8

--- scanner 0 ---
1,1,1
2,2,2
3,3,3
3,1,2
-6,-4,-5
0,7,-8

By finding pairs of scanners that both see at least 12 of the same beacons, you can assemble the entire map.
Assemble the full map of beacons. How many beacons are there?
    */

    private val scanners: MutableList<Scanner> = mutableListOf()
    private val scannerRegex = Regex("--- scanner (\\d+) ---")

    fun getResultPart1() {
        var currentScanner = Scanner(-1)
        getInputAsSequence().forEach { line ->
            val matchResult = scannerRegex.matchEntire(line)
            if (matchResult != null) {
                currentScanner = Scanner(matchResult.groups[1]!!.value.toInt())
                scanners.add(currentScanner)
            } else if (line.contains(',')) {
                val pointParts = line.split(",")
                val point = Point.of(pointParts[0].toInt(), pointParts[1].toInt())
                currentScanner.addBeacon(point)
            }
        }

        scanners.forEach {
            println(it.getBeaconMap())
        }
    }

    private data class Scanner(val number: Int) {

        private val beacons: MutableList<Point> = mutableListOf()

        fun addBeacon(point: Point) {
            beacons.add(point)
        }

        fun getBeaconMap(): String {
            val minY = min(0, beacons.minOf { it.y })
            var maxY = beacons.maxOf { it.y }
            if (maxY < 0) maxY = 0
            val minX = min(0, beacons.minOf { it.x })
            var maxX = beacons.maxOf { it.x }
            if (maxX < 0) maxX = 0

            val sb = StringBuilder()
            (maxY downTo minY).forEach { y ->
                (minX..maxX).forEach { x ->
                    if (beacons.contains(Point.of(x, y))) {
                        sb.append('B')
                    } else {
                        sb.append('.')
                    }

                    if (y == 0 && x == 0) {
                        sb.append('S')
                    }
                }
                sb.appendLine()
            }

            return sb.toString()
        }

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("--- scanner $number ---").appendLine()
            beacons.forEach {
                sb.append(it).appendLine()
            }
            return sb.toString()
        }
    }

    /*
    */

    fun getResultPart2() {
    }
}
