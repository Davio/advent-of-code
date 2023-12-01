//package com.github.davio.aoc.y2020
//
//import com.github.davio.aoc.general.call
//import com.github.davio.aoc.general.getInputAsSequence
//import com.github.davio.aoc.general.permutations
//import java.lang.System.lineSeparator
//import kotlin.system.measureTimeMillis
//
//fun main() {
//    measureTimeMillis {
//        Day20.getResult()
//    }.call { println("$it ms") }
//}
//
//object Day20 : Day() {
//
//    /*
//     * --- Day 20: Jurassic Jigsaw ---
//
//The high-speed train leaves the forest and quickly carries you south. You can even see a desert in the distance!
//* Since you have some spare time, you might as well see if there was anything interesting in the image the
//* Mythical Information Bureau satellite captured.
//
//After decoding the satellite messages,
//* you discover that the data actually contains many small images created by the satellite's camera array.
//* The camera array consists of many cameras; rather than produce a single square image,
//* they produce many smaller square image tiles that need to be reassembled back into a single image.
//
//Each camera in the camera array returns a single monochrome image tile with a random unique ID number.
//* The tiles (your puzzle input) arrived in a random order.
//
//Worse yet, the camera array appears to be malfunctioning: each image tile has been rotated and flipped to a random orientation.
//* Your first task is to reassemble the original image by orienting the tiles so they fit together.
//
//To show how the tiles should be reassembled, each tile's image data includes a border that should line up exactly with its adjacent tiles.
//* All tiles have this border, and the border lines up exactly when the tiles are both oriented correctly.
//* Tiles at the edge of the image also have this border, but the outermost edges won't line up with any other tiles.
//
//For example, suppose you have the following nine tiles:
//
//Tile 2311:
//..##.#..#.
//##..#.....
//#...##..#.
//####.#...#
//##.##.###.
//##...#.###
//.#.#.#..##
//..#....#..
//###...#.#.
//..###..###
//
//Tile 1951:
//#.##...##.
//#.####...#
//.....#..##
//#...######
//.##.#....#
//.###.#####
//###.##.##.
//.###....#.
//..#.#..#.#
//#...##.#..
//
//Tile 1171:
//####...##.
//#..##.#..#
//##.#..#.#.
//.###.####.
//..###.####
//.##....##.
//.#...####.
//#.##.####.
//####..#...
//.....##...
//
//Tile 1427:
//###.##.#..
//.#..#.##..
//.#.##.#..#
//#.#.#.##.#
//....#...##
//...##..##.
//...#.#####
//.#.####.#.
//..#..###.#
//..##.#..#.
//
//Tile 1489:
//##.#.#....
//..##...#..
//.##..##...
//..#...#...
//#####...#.
//#..#.#.#.#
//...#.#.#..
//##.#...##.
//..##.##.##
//###.##.#..
//
//Tile 2473:
//#....####.
//#..#.##...
//#.##..#...
//######.#.#
//.#...#.#.#
//.#########
//.###.#..#.
//########.#
//##...##.#.
//..###.#.#.
//
//Tile 2971:
//..#.#....#
//#...###...
//#.#.###...
//##.##..#..
//.#####..##
//.#..####.#
//#..#.#..#.
//..####.###
//..#.#.###.
//...#.#.#.#
//
//Tile 2729:
//...#.#.#.#
//####.#....
//..#.#.....
//....#..#.#
//.##..##.#.
//.#.####...
//####.#.#..
//##.####...
//##..#.##..
//#.##...##.
//
//Tile 3079:
//#.#.#####.
//.#..######
//..#.......
//######....
//####.#..#.
//.#...#.##.
//#.#####.##
//..#.###...
//..#.......
//..#.###...
//
//By rotating, flipping, and rearranging them, you can find a square arrangement that causes all adjacent borders to line up:
//
//#...##.#.. ..###..### #.#.#####.
//..#.#..#.# ###...#.#. .#..######
//.###....#. ..#....#.. ..#.......
//###.##.##. .#.#.#..## ######....
//.###.##### ##...#.### ####.#..#.
//.##.#....# ##.##.###. .#...#.##.
//#...###### ####.#...# #.#####.##
//.....#..## #...##..#. ..#.###...
//#.####...# ##..#..... ..#.......
//#.##...##. ..##.#..#. ..#.###...
//
//#.##...##. ..##.#..#. ..#.###...
//##..#.##.. ..#..###.# ##.##....#
//##.####... .#.####.#. ..#.###..#
//####.#.#.. ...#.##### ###.#..###
//.#.####... ...##..##. .######.##
//.##..##.#. ....#...## #.#.#.#...
//....#..#.# #.#.#.##.# #.###.###.
//..#.#..... .#.##.#..# #.###.##..
//####.#.... .#..#.##.. .######...
//...#.#.#.# ###.##.#.. .##...####
//
//...#.#.#.# ###.##.#.. .##...####
//..#.#.###. ..##.##.## #..#.##..#
//..####.### ##.#...##. .#.#..#.##
//#..#.#..#. ...#.#.#.. .####.###.
//.#..####.# #..#.#.#.# ####.###..
//.#####..## #####...#. .##....##.
//##.##..#.. ..#...#... .####...#.
//#.#.###... .##..##... .####.##.#
//#...###... ..##...#.. ...#..####
//..#.#....# ##.#.#.... ...##.....
//
//For reference, the IDs of the above tiles are:
//
//1951    2311    3079
//2729    1427    2473
//2971    1489    1171
//
//To check that you've assembled the image correctly, multiply the IDs of the four corner tiles together.
//* If you do this with the assembled tiles from the example above, you get 1951 * 3079 * 2971 * 1171 = 20899048083289.
//
//Assemble the tiles into an image. What do you get if you multiply together the IDs of the four corner tiles?
//     */
//
//    private val input = getInputAsSequence()
//    private val tiles = arrayListOf<Tile>()
//    private var currentTile: Tile? = null
//
//    fun getResult() {
//        var y = 0
//
//        input.forEach { line ->
//            when {
//                line.startsWith("Tile") -> {
//                    currentTile = Tile(line.substring(5, line.lastIndex).toInt())
//                    tiles.add(currentTile!!)
//                }
//                line.isNotBlank() -> {
//                    line.forEachIndexed { x, char ->
//                        currentTile!!.addPoint(Point(x, y, char == '#'))
//                    }
//                    y += 1
//                }
//                else -> {
//                    y = 0
//                    currentTile!!.initBorders()
//                }
//            }
//        }
//
//        currentTile!!.initBorders()
//
//        getAllTileConfigurations().first {
//            puzzleIsComplete(it)
//        }.forEach { println(it) }
//    }
//
//    private fun puzzleIsComplete(tileConfiguration: List<Tile>): Boolean {
//        return true
//    }
//
//    private fun getAllTileConfigurations(): Sequence<List<Tile>> {
//        return tiles.permutations()
//    }
//
//    private data class Tile(val id: Int) {
//
//        var borders: IntArray = intArrayOf()
//
//        private val points = ArrayList<Point>(100)
//
//        fun addPoint(point: Point) {
//            points.add(point)
//        }
//
//        fun initBorders() {
//            val maxX = points.maxOf { it.x }
//            val maxY = points.maxOf { it.y }
//            val topBorder = getBorder(xRange = (0..maxX))
//            val rightBorder = getBorder(xRange = (maxX..maxX), yRange = (0..maxY))
//            val bottomBorder = getBorder(xRange = (0..maxX), yRange = (maxY..maxY))
//            val leftBorder = getBorder(yRange = (0..maxY))
//            borders = intArrayOf(topBorder, rightBorder, bottomBorder, leftBorder)
//        }
//
//        fun getAllForms(): Sequence<IntArray> {
//            return sequence {
//                yield(borders)
//                var rotatedBorders = borders
//                repeat(3) {
//                    rotatedBorders = getRotatedBorders(rotatedBorders)
//                    yield(rotatedBorders)
//                }
//
//                var flippedBorders = getFlippedBorders(borders)
//                yield(flippedBorders)
//
//                repeat(3) {
//                    flippedBorders = getRotatedBorders(flippedBorders)
//                    yield(flippedBorders)
//                }
//            }
//        }
//
//        fun getRotatedBorders(currentBorders: IntArray): IntArray {
//            val topBorder = currentBorders[3].toString(2).reversed().toInt(2)
//            val rightBorder = currentBorders[0]
//            val bottomBorder = currentBorders[1].toString(2).reversed().toInt(2)
//            val leftBorder = currentBorders[2]
//
//            return intArrayOf(topBorder, rightBorder, bottomBorder, leftBorder)
//        }
//
//        fun getFlippedBorders(currentBorders: IntArray): IntArray {
//            val topBorder = currentBorders[2]
//            val rightBorder = currentBorders[3]
//            val bottomBorder = currentBorders[0]
//            val leftBorder = currentBorders[1]
//
//            return intArrayOf(topBorder, rightBorder, bottomBorder, leftBorder)
//        }
//
//        private fun getBorder(xRange: IntRange = (0 until 1), yRange: IntRange = (0 until 1)): Int {
//            return xRange.flatMap { x ->
//                yRange.map { y ->
//                    val matchingPoint = points.find { it.x == x && it.y == y }
//                    if (matchingPoint!!.active) '1' else '0'
//                }
//            }.joinToString("").toInt(2)
//        }
//
//        override fun toString(): String {
//            var result = "Tile $id:" + lineSeparator()
//            var currentY = 0
//
//            result += points.joinToString(separator = "") {
//                var pointStr = ""
//                if (it.y > currentY) {
//                    pointStr += lineSeparator()
//                    currentY = it.y
//                }
//                pointStr += if (it.active) "#" else "."
//                pointStr
//            }
//
//            return result + lineSeparator()
//        }
//    }
//
//    private data class Point.of(val x: Int, val y: Int, val active: Boolean = false)
//}
