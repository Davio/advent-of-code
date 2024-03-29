package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.Point
import com.github.davio.aoc.general.getInputAsSequence
import kotlin.math.*

fun main() {
    Day12.getResult()
}

object Day12 : Day() {

    /*
     * --- Day 12: Rain Risk ---

Your ferry made decent progress toward the island, but the storm came in faster than anyone expected.
* The ferry needs to take evasive actions!

Unfortunately, the ship's navigation computer seems to be malfunctioning; rather than giving a route directly to safety,
* it produced extremely circuitous instructions. When the captain uses the PA system to ask if anyone can help, you quickly volunteer.

The navigation instructions (your puzzle input) consists of a sequence of single-character actions paired with integer input values.
* After staring at them for a few minutes, you work out what they probably mean:

    Action N means to move north by the given value.
    Action S means to move south by the given value.
    Action E means to move east by the given value.
    Action W means to move west by the given value.
    Action L means to turn left the given number of degrees.
    Action R means to turn right the given number of degrees.
    Action F means to move forward by the given value in the direction the ship is currently facing.

The ship starts by facing east. Only the L and R actions change the direction the ship is facing.
* (That is, if the ship is facing east and the next instruction is N10, the ship would move north 10 units,
* but would still move east if the following action were F.)

For example:

F10
N3
F7
R90
F11

These instructions would be handled as follows:

    F10 would move the ship 10 units east (because the ship starts by facing east) to east 10, north 0.
    N3 would move the ship 3 units north to east 10, north 3.
    F7 would move the ship another 7 units east (because the ship is still facing east) to east 17, north 3.
    R90 would cause the ship to turn right by 90 degrees and face south; it remains at east 17, north 3.
    F11 would move the ship 11 units south to east 17, south 8.

At the end of these instructions, the ship's Manhattan distance
* (sum of the absolute values of its east/west position and its north/south position) from its starting position is 17 + 8 = 25.

Figure out where the navigation instructions lead. What is the Manhattan distance between that location and the ship's starting position?
*
* --- Part Two ---

Before you can give the destination to the captain,
* you realize that the actual action meanings were printed on the back of the instructions the whole time.

Almost all of the actions indicate how to move a waypoint which is relative to the ship's position:

    Action N means to move the waypoint north by the given value.
    Action S means to move the waypoint south by the given value.
    Action E means to move the waypoint east by the given value.
    Action W means to move the waypoint west by the given value.
    Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
    Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
    Action F means to move forward to the waypoint a number of times equal to the given value.

The waypoint starts 10 units east and 1 unit north relative to the ship.
* The waypoint is relative to the ship; that is, if the ship moves, the waypoint moves with it.

For example, using the same instructions as above:

    F10 moves the ship to the waypoint 10 times (a total of 100 units east and 10 units north), leaving the ship at east 100, north 10.
    * The waypoint stays 10 units east and 1 unit north of the ship.
    *
    N3 moves the waypoint 3 units north to 10 units east and 4 units north of the ship.
    * The ship remains at east 100, north 10.
    *
    F7 moves the ship to the waypoint 7 times (a total of 70 units east and 28 units north), leaving the ship at east 170, north 38.
    * The waypoint stays 10 units east and 4 units north of the ship.
    *
    R90 rotates the waypoint around the ship clockwise 90 degrees, moving it to 4 units east and 10 units south of the ship.
    *  The ship remains at east 170, north 38.
    F11 moves the ship to the waypoint 11 times (a total of 44 units east and 110 units south), leaving the ship at east 214, south 72.
    * The waypoint stays 4 units east and 10 units south of the ship.

After these operations, the ship's Manhattan distance from its starting position is 214 + 72 = 286.

Figure out where the navigation instructions actually lead. What is the Manhattan distance between that location and the ship's starting position?
     */

    fun getResult() {
        val ship = Ship()
        getInputAsSequence().forEach {
            val direction = Direction.fromChar(it[0])
            val amount = it.substring(1).toInt()
            val movement = Movement(direction, amount)
            ship.performAction(movement)
        }

        println(abs(ship.position.x) + abs(ship.position.y))
    }

    private class Ship {

        var position = Point.ZERO
        var waypointPosition = Point.of(10, 1)

        private var headingInDegrees = 0.0
        private var headingInCoords = Pair(1, 0) // East

        @Suppress("unused") // Only used by part 1
        fun changeHeading(direction: Direction, degrees: Int) {
            headingInDegrees = (headingInDegrees + if (direction == Direction.RIGHT) degrees else 360 - degrees) % 360
            val headingInRadians = headingInDegrees.toRadians()
            headingInCoords = Pair(cos(headingInRadians).roundToInt(), -sin(headingInRadians).roundToInt())
            println("New heading is $headingInCoords")
        }

        fun rotateWaypoint(direction: Direction, degrees: Int) {
            val normalizedWaypointX = waypointPosition.x - position.x
            val normalizedWaypointY = waypointPosition.y - position.y

            val normalizedWaypointVector = if (degrees == 180) {
                Point.of(-normalizedWaypointX, -normalizedWaypointY)
            } else if ((degrees == 90 && direction == Direction.RIGHT)
                || (degrees == 270 && direction == Direction.LEFT)) {
                Point.of(normalizedWaypointY, -normalizedWaypointX)
            } else {
                Point.of(-normalizedWaypointY, normalizedWaypointX)
            }

            waypointPosition = position + normalizedWaypointVector
        }

        fun performAction(movement: Movement) {
            if (movement.direction == Direction.RIGHT || movement.direction == Direction.LEFT) {
                println("Turning ${movement.direction} ${movement.amount} degrees")
                rotateWaypoint(movement.direction, movement.amount)
            } else if (movement.direction != Direction.FORWARD) {
                println("Moving waypoint ${movement.amount} ${movement.direction}")
                waypointPosition = when (movement.direction) {
                    Direction.NORTH -> waypointPosition up movement.amount
                    Direction.EAST -> waypointPosition right movement.amount
                    Direction.SOUTH -> waypointPosition down movement.amount
                    Direction.WEST -> waypointPosition left movement.amount
                    else -> waypointPosition
                }
            } else {
                println("Moving ship towards waypoint ${movement.amount} steps")
                val forwardVector = Point.of(
                    movement.amount * (waypointPosition.x - position.x),
                    movement.amount * (waypointPosition.y - position.y))
                position += forwardVector
                waypointPosition += forwardVector
            }

            println("Ship position $position")
            println("Waypoint position $waypointPosition")
        }

        private fun Double.toRadians() = (this * PI) / 180.0
    }

    private data class Movement(val direction: Direction, val amount: Int)

    private enum class Direction(val char: Char) {
        NORTH('N'),
        EAST('E'),
        SOUTH('S'),
        WEST('W'),
        FORWARD('F'),
        RIGHT('R'),
        LEFT('L');

        companion object {
            fun fromChar(c: Char) = entries.first { it.char == c }
        }
    }
}
