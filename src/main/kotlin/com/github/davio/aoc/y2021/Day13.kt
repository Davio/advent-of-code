package com.github.davio.aoc.y2021

import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import kotlin.system.measureTimeMillis

fun main() {
    Day13.parseInput()
    Day13.getResultPart1()
    measureTimeMillis {
        Day13.getResultPart2()
    }.call { println("Took $it ms") }
}

object Day13 {

    /*
    --- Day 13: Transparent Origami ---

You reach another volcanically active part of the cave. It would be nice if you could do some kind of thermal imaging so you could tell ahead of time which caves are too hot to safely enter.

Fortunately, the submarine seems to be equipped with a thermal camera! When you activate it, you are greeted with:

Congratulations on your purchase! To activate this infrared thermal imaging
camera system, please enter the code found on page 1 of the manual.

Apparently, the Elves have never used this feature. To your surprise, you manage to find the manual; as you go to open it, page 1 falls out. It's a large sheet of transparent paper! The transparent paper is marked with random dots and includes instructions on how to fold it up (your puzzle input). For example:

6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5

The first section is a list of dots on the transparent paper. 0,0 represents the top-left coordinate. The first value, x, increases to the right. The second value, y, increases downward. So, the coordinate 3,0 is to the right of 0,0, and the coordinate 0,7 is below 0,0. The coordinates in this example form the following pattern, where # is a dot on the paper and . is an empty, unmarked position:

...#..#..#.
....#......
...........
#..........
...#....#.#
...........
...........
...........
...........
...........
.#....#.##.
....#......
......#...#
#..........
#.#........

Then, there is a list of fold instructions. Each instruction indicates a line on the transparent paper and wants you to fold the paper up (for horizontal y=... lines) or left (for vertical x=... lines). In this example, the first fold instruction is fold along y=7, which designates the line formed by all of the positions where y is 7 (marked here with -):

...#..#..#.
....#......
...........
#..........
...#....#.#
...........
...........
-----------
...........
...........
.#....#.##.
....#......
......#...#
#..........
#.#........

Because this is a horizontal line, fold the bottom half up. Some of the dots might end up overlapping after the fold is complete, but dots will never appear exactly on a fold line. The result of doing this fold looks like this:

#.##..#..#.
#...#......
......#...#
#...#......
.#.#..#.###
...........
...........

Now, only 17 dots are visible.

Notice, for example, the two dots in the bottom left corner before the transparent paper is folded; after the fold is complete, those dots appear in the top left corner (at 0,0 and 0,1). Because the paper is transparent, the dot just below them in the result (at 0,3) remains visible, as it can be seen through the transparent paper.

Also notice that some dots can end up overlapping; in this case, the dots merge together and become a single dot.

The second fold instruction is fold along x=5, which indicates this line:

#.##.|#..#.
#...#|.....
.....|#...#
#...#|.....
.#.#.|#.###
.....|.....
.....|.....

Because this is a vertical line, fold left:

#####
#...#
#...#
#...#
#####
.....
.....

The instructions made a square!

The transparent paper is pretty big, so for now, focus on just completing the first fold. After the first fold in the example above, 17 dots are visible - dots that end up overlapping after the fold is completed count as a single dot.

How many dots are visible after completing just the first fold instruction on your transparent paper?
    */

    private val originalPaper = TransparentPaper()
    private val foldInstructions = mutableListOf<FoldInstruction>()
    private val dotPattern = Regex("(\\d+),(\\d+)")
    private val foldInstructionPattern = Regex("fold along ([yx])=(\\d+)")

    fun parseInput() {
        var parsingDots = true

        getInputAsList().forEach { line ->
            if (parsingDots) {
                if (line.isBlank()) {
                    parsingDots = false
                } else {
                    val (x, y) = dotPattern.matchEntire(line)!!.destructured
                    val dot = Point.of(x.toInt(), y.toInt())
                    originalPaper.markPoint(dot)
                }
            } else {
                val (axis, indexStr) = foldInstructionPattern.matchEntire(line)!!.destructured
                val index = indexStr.toInt()
                val foldInstruction: FoldInstruction = if (axis == "y") {
                    FoldInstruction(alongY = index)
                } else {
                    FoldInstruction(alongX = index)
                }
                foldInstructions.add(foldInstruction)
            }

        }

        println(originalPaper)
    }

    fun getResultPart1() {
        var paper = originalPaper
        foldInstructions.take(1).forEach {
            paper = it.fold(paper)
            println(paper)
        }
        println(paper.grid.flatten().count { it })
    }

    /*
   --- Part Two ---

Finish folding the transparent paper according to the instructions. The manual says the code is always eight capital letters.

What code do you use to activate the infrared thermal imaging camera system?
    */

    fun getResultPart2() {
        var paper = originalPaper
        foldInstructions.forEach {
            paper = it.fold(paper)
            println(paper)
        }
        println(paper.grid.flatten().count { it })
    }

    private class TransparentPaper {

        val grid: MutableList<MutableList<Boolean>> = mutableListOf(mutableListOf(false))

        fun markPoint(dot: Point, isDot: Boolean = true) {
            val x = dot.x
            val y = dot.y
            val maxX = getMaxX()
            val maxY = getMaxY()

            if (y > maxY) {
                repeat((maxY until y).count()) {
                    addRow()
                }
            }

            if (x > maxX) {
                repeat((maxX until x).count()) {
                    addColumn()
                }
            }

            grid[y][x] = isDot
        }

        private fun addRow() {
            val row = (0..getMaxX()).map { false }.toMutableList()
            grid.add(row)
        }

        private fun addColumn() {
            grid.forEach { row ->
                row.add(false)
            }
        }

        private fun getMaxX() = grid.firstOrNull()?.lastIndex ?: -1
        private fun getMaxY() = grid.lastIndex

        override fun toString(): String {
            val stringBuilder = StringBuilder()
            grid.indices.forEach { y ->
                grid[y].indices.forEach { x ->
                    stringBuilder.append(if (grid[y][x]) "#" else ".")
                }
                stringBuilder.appendLine()
            }
            return stringBuilder.toString()
        }
    }

    private data class FoldInstruction(val alongY: Int = 0, val alongX: Int = 0) {

        fun fold(paper: TransparentPaper): TransparentPaper {

            val foldedPaper = if (alongY != 0) {
                foldPaperAlongY(paper)
            } else {
                foldPaperAlongX(paper)
            }

            return foldedPaper
        }

        private fun foldPaperAlongY(paper: TransparentPaper): TransparentPaper {
            val formattedPaper = paper.toString()
            val rowSize = paper.grid[0].size
            val startIndex = alongY * (rowSize + 1)
            val endIndex = startIndex + rowSize
            println(formattedPaper.replaceRange(startIndex, endIndex, "-".repeat(rowSize)))

            val foldedPaper = TransparentPaper()
            (0 until alongY).forEach { yToKeep ->
                paper.grid[yToKeep].indices.forEach { xToKeep ->
                    foldedPaper.markPoint(Point.of(xToKeep, yToKeep), paper.grid[yToKeep][xToKeep])
                }
            }
            (alongY + 1..paper.grid.lastIndex).forEachIndexed { index, yToCheck ->
                paper.grid[yToCheck].indices.forEach { xToCheck ->
                    val newY = alongY - (index + 1)
                    foldedPaper.markPoint(Point.of(xToCheck, newY), paper.grid[yToCheck][xToCheck] || paper.grid[newY][xToCheck])
                }
            }

            return foldedPaper
        }

        private fun foldPaperAlongX(paper: TransparentPaper): TransparentPaper {
            var formattedPaper = paper.toString()
            val rowSize = paper.grid[0].size

            paper.grid.indices.forEach { y ->
                val startIndex = y * (rowSize + 1) + alongX
                val endIndex = startIndex + 1
                formattedPaper = formattedPaper.replaceRange(startIndex, endIndex, "|")
            }
            println(formattedPaper)

            val foldedPaper = TransparentPaper()
            paper.grid.indices.forEach { yToKeep ->
                (0 until alongX).forEach { xToKeep ->
                    foldedPaper.markPoint(Point.of(xToKeep, yToKeep), paper.grid[yToKeep][xToKeep])
                }
            }
            paper.grid.indices.forEach { yToCheck ->
                (alongX + 1 until rowSize).forEachIndexed { index, xToCheck ->
                    val newX = alongX - (index + 1)
                    foldedPaper.markPoint(Point.of(newX, yToCheck), paper.grid[yToCheck][xToCheck] || paper.grid[yToCheck][newX])
                }
            }

            return foldedPaper
        }
    }
}
