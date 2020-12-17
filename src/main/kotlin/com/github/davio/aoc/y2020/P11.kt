package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.getInputAsSequence
import java.lang.System.lineSeparator

fun main() {
    P11().getResult()
}

class P11 {

    /*
     * --- Day 10: Adapter Array ---

--- Day 11: Seating System ---

Your plane lands with plenty of time to spare.
* The final leg of your journey is a ferry that goes directly to the tropical island where you can finally start your vacation.
* As you reach the waiting area to board the ferry, you realize you're so early, nobody else has even arrived yet!

By modeling the process people use to choose (or abandon) their seat in the waiting area,
* you're pretty sure you can predict the best place to sit.
* You make a quick map of the seat layout (your puzzle input).

The seat layout fits neatly on a grid. Each position is either floor (.), an empty seat (L), or an occupied seat (#).
* For example, the initial seat layout might look like this:

L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL

Now, you just need to model the people who will be arriving shortly.
* Fortunately, people are entirely predictable and always follow a simple set of rules.
* All decisions are based on the number of occupied seats adjacent to a given seat
* (one of the eight positions immediately up, down, left, right, or diagonal from the seat).
* The following rules are applied to every seat simultaneously:

    If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
    If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
    Otherwise, the seat's state does not change.

Floor (.) never changes; seats don't move, and nobody sits on the floor.

After one round of these rules, every seat in the example layout becomes occupied:

#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##

After a second round, the seats with four or more occupied adjacent seats become empty again:

#.LL.L#.##
#LLLLLL.L#
L.L.L..L..
#LLL.LL.L#
#.LL.LL.LL
#.LLLL#.##
..L.L.....
#LLLLLLLL#
#.LLLLLL.L
#.#LLLL.##

This process continues for three more rounds:

#.##.L#.##
#L###LL.L#
L.#.#..#..
#L##.##.L#
#.##.LL.LL
#.###L#.##
..#.#.....
#L######L#
#.LL###L.L
#.#L###.##

#.#L.L#.##
#LLL#LL.L#
L.L.L..#..
#LLL.##.L#
#.LL.LL.LL
#.LL#L#.##
..L.L.....
#L#LLLL#L#
#.LLLLLL.L
#.#L#L#.##

#.#L.L#.##
#LLL#LL.L#
L.#.L..#..
#L##.##.L#
#.#L.LL.LL
#.#L#L#.##
..L.L.....
#L#L##L#L#
#.LLLLLL.L
#.#L#L#.##

At this point, something interesting happens: the chaos stabilizes and further applications of these rules cause no seats to change state!
* Once people stop moving around, you count 37 occupied seats.

Simulate your seating area by applying the seating rules repeatedly until no seats change state. How many seats end up occupied?
*
* --- Part Two ---

As soon as people start to arrive, you realize your mistake.
* People don't just care about adjacent seats - they care about the first seat they can see in each of those eight directions!

Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those eight directions.
* For example, the empty seat below would see eight occupied seats:

.......#.
...#.....
.#.......
.........
..#L....#
....#....
.........
#........
...#.....

The leftmost empty seat below would only see one empty seat, but cannot see any of the occupied ones:

.............
.L.L.#.#.#.#.
.............

The empty seat below would see no occupied seats:

.##.##.
#.#.#.#
##...##
...L...
##...##
#.#.#.#
.##.##.

Also, people seem to be more tolerant than you expected:
* it now takes five or more visible occupied seats for an occupied seat to become empty (rather than four or more from the previous rules).
* The other rules still apply: empty seats that see no occupied seats become occupied, seats matching no rule don't change, and floor never changes.

Given the same starting layout as above, these new rules cause the seating area to shift around as follows:

L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL

#.##.##.##
#######.##
#.#.#..#..
####.##.##
#.##.##.##
#.#####.##
..#.#.....
##########
#.######.#
#.#####.##

#.LL.LL.L#
#LLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLL#
#.LLLLLL.L
#.LLLLL.L#

#.L#.##.L#
#L#####.LL
L.#.#..#..
##L#.##.##
#.##.#L.##
#.#####.#L
..#.#.....
LLL####LL#
#.L#####.L
#.L####.L#

#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##LL.LL.L#
L.LL.LL.L#
#.LLLLL.LL
..L.L.....
LLLLLLLLL#
#.LLLLL#.L
#.L#LL#.L#

#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##L#.#L.L#
L.L#.#L.L#
#.L####.LL
..#.#.....
LLL###LLL#
#.LLLLL#.L
#.L#LL#.L#

#.L#.L#.L#
#LLLLLL.LL
L.L.L..#..
##L#.#L.L#
L.L#.LL.L#
#.LLLL#.LL
..#.L.....
LLL###LLL#
#.LLLLL#.L
#.L#LL#.L#

Again, at this point, people stop shifting around and the seating area reaches equilibrium. Once this occurs, you count 26 occupied seats.

Given the new visibility method and the rule change for occupied seats becoming empty, once equilibrium is reached, how many seats end up occupied?
     */

    private val inputList = getInputAsList()
    private val seatingLayout = SeatingLayout(inputList.size)

    fun getResult() {
        getInputAsSequence().forEach {
            seatingLayout.parseLine(it)
        }
        seatingLayout.processSeating()

        println(seatingLayout.getNumberOfOccupiedSeats())
    }

    private class SeatingLayout(listSize: Int) {

        private val layout: MutableList<MutableList<PositionType>> = ArrayList(listSize)
        private val positionsToChange = hashMapOf<PositionType, MutableList<Pair<Int, Int>>>()

        init {
            positionsToChange[PositionType.OCCUPIED] = arrayListOf()
            positionsToChange[PositionType.EMPTY] = arrayListOf()
        }

        fun parseLine(line: String) {
            line.map { PositionType.fromChar(it) }.call { this.addRow(it.toMutableList()) }
        }

        fun addRow(positions: MutableList<PositionType>) {
            layout.add(positions)
        }

        fun processSeating() {
            layout.forEachIndexed { row, positions ->
                positions.forEachIndexed { col, positionType ->
                    if (positionType != PositionType.FLOOR) {
                        if (shouldBeOccupied(row, col, positionType)) {
                            positionsToChange[PositionType.OCCUPIED]!!.add(Pair(row, col))
                        } else if (shouldBeEmptied(row, col, positionType)) {
                            positionsToChange[PositionType.EMPTY]!!.add(Pair(row, col))
                        }
                    }
                }
            }

            if (positionsToChange.values.all { it.isEmpty() }) {
                println("No more changes")
                return
            }

            positionsToChange.forEach { entry ->
                entry.value.forEach {
                    layout[it.first][it.second] = entry.key
                }
            }

            positionsToChange[PositionType.OCCUPIED]!!.clear()
            positionsToChange[PositionType.EMPTY]!!.clear()

            println(this)

            processSeating()
        }

        private fun shouldBeOccupied(row: Int, col: Int, positionType: PositionType): Boolean {
            return positionType == PositionType.EMPTY && getVisibleSeats(row, col).all { it == PositionType.EMPTY }
        }

        private fun shouldBeEmptied(row: Int, col: Int, positionType: PositionType): Boolean {
            return positionType == PositionType.OCCUPIED && getVisibleSeats(row, col).count { it == PositionType.OCCUPIED } >= 5
        }

        @Suppress("unused")
        private fun getAdjacentSeats(myRow: Int, myCol: Int): List<PositionType> {
            val startRow = myRow - 1
            val endRow = myRow + 1
            val startCol = myCol - 1
            val endCol = myCol + 1

            return (startRow..endRow).flatMap { rowIndex ->
                (startCol..endCol).filter { colIndex ->
                    rowIndex in (0..layout.lastIndex)
                            && colIndex in (0..layout[0].lastIndex)
                            && !(rowIndex == myRow && colIndex == myCol)
                            && layout[rowIndex][colIndex] != PositionType.FLOOR
                }.map { col ->
                    layout[rowIndex][col]
                }
            }
        }

        private fun getVisibleSeats(myRow: Int, myCol: Int): List<PositionType> {
            return (-1..1).flatMap { rowDirection ->
                (-1..1).filter { colDirection ->
                    !(rowDirection == 0 && colDirection == 0)
                }.mapNotNull { colDirection ->
                    getFirstVisibleSeat(myRow, myCol, rowDirection, colDirection)
                }
            }
        }

        private fun getFirstVisibleSeat(myRow: Int, myCol: Int, rowDirection: Int, colDirection: Int): PositionType? {
            var row = myRow + rowDirection
            var col = myCol + colDirection

            while (row in (0..layout.lastIndex) && col in (0..layout[0].lastIndex)) {
                val position = layout[row][col]
                if (position != PositionType.FLOOR) {
                    return position
                }

                row += rowDirection
                col += colDirection
            }

            return null
        }

        fun getNumberOfOccupiedSeats() = layout.fold(0) { acc, row ->
            acc + row.count { positionType -> positionType == PositionType.OCCUPIED }
        }

        override fun toString(): String {
            return layout.fold("") { acc, row ->
                acc + getRowString(row)
            }
        }

        private fun getRowString(row: MutableList<PositionType>) = row.joinToString(
            separator = "",
            postfix = lineSeparator(),
            transform = { positionType -> positionType.char.toString() })
    }

    private enum class PositionType(val char: Char) {
        EMPTY('L'),
        OCCUPIED('#'),
        FLOOR('.');

        companion object {
            fun fromChar(c: Char) = values().first { it.char == c }
        }
    }
}