package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputReader
import kotlin.system.measureTimeMillis

fun main() {
    println(Day10.getResultPart1())
    measureTimeMillis {
        println(Day10.getResultPart2())
    }.call { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day X](https://adventofcode.com/2022/day/X#part2])
 */
object Day10 : Day() {

    private sealed class Command(val cyclesNeeded: Int)
    private object Noop : Command(1)
    private data class AddX(val amount: Int) : Command(2)

    private object Cpu {
        private var cyclesUsed = 0
        var currentCommand: Command? = null

        val busy: Boolean get() = currentCommand != null

        fun performCycle(currentX: Int): Int {
            val command = currentCommand!!
            cyclesUsed++

            val newX = when (command) {
                is Noop -> {
                    currentX
                }

                is AddX -> if (cyclesUsed == command.cyclesNeeded) {
                    currentX + command.amount
                } else {
                    currentX
                }
            }

            if (cyclesUsed == command.cyclesNeeded) {
                currentCommand = null
                cyclesUsed = 0
            }

            return newX
        }
    }

    private fun parseCommand(line: String): Command =
        if (line.startsWith("addx")) {
            AddX(line.split(" ")[1].toInt())
        } else Noop

    fun getResultPart1(): Int {
        var x = 1
        return getInputReader().use { reader ->
            generateSequence(1) { it + 1 }.takeWhile { Cpu.busy || reader.ready() }.map { cycle ->
                if (!Cpu.busy) {
                    val command = parseCommand(reader.readLine())
                    Cpu.currentCommand = command
                }
                val newX = Cpu.performCycle(x)
                val signalStrength = if ((cycle - 20) % 40 == 0) {
                    cycle * x
                } else {
                    0
                }
                x = newX
                signalStrength
            }.sum()
        }
    }

    fun getResultPart2(): Int {
        var x = 1
        val crt = Array(6) { BooleanArray(40) { false } }
        return getInputReader().use { reader ->
            generateSequence(1) { it + 1 }.takeWhile { Cpu.busy || reader.ready() }.map { cycle ->
                val rowIndex = (cycle - 1) / 40
                val pixelIndex = (cycle - 1) % 40

                if (!Cpu.busy) {
                    val command = parseCommand(reader.readLine())
                    Cpu.currentCommand = command
                }
                val newX = Cpu.performCycle(x)
                crt[rowIndex][pixelIndex] = pixelIndex in (x - 1..x + 1)
                drawCrt(crt)
                x = newX
                0
            }.sum()
        }
    }

    private fun drawCrt(crt: Array<BooleanArray>) {
        println("=".repeat(crt[0].size))
        (0..crt.lastIndex).forEach { y ->
            (0..crt[0].lastIndex).forEach { x ->
                if (crt[y][x]) print("#") else print(".")
            }
            println()
        }
    }
}
