package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsSequence
import kotlin.system.measureTimeMillis

fun main() {
    println(Day7.getResultPart1())
    measureTimeMillis {
        println(Day7.getResultPart2())
    }.also { println("Took $it ms") }
}

/**
 * See [Advent of Code 2022 Day 7](https://adventofcode.com/2022/day/7#part2])
 */
object Day7 : Day() {

    private sealed interface TerminalOutput
    private sealed interface Command : TerminalOutput
    private data class ChangeDirectory(val target: String) : Command
    private object ListDirectory : Command
    private sealed interface DirectoryOrFileOutput : TerminalOutput {
        fun getTotalSize(): Long
    }

    private data class Directory(
        val name: String,
        var parent: Directory? = null,
        val subdirectories: MutableList<Directory> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) : TerminalOutput, DirectoryOrFileOutput {
        override fun getTotalSize(): Long = subdirectories.sumOf { it.getTotalSize() } + files.sumOf { it.getTotalSize() }
    }

    private data class File(val name: String, val size: Long) : TerminalOutput, DirectoryOrFileOutput {
        override fun getTotalSize() = size
    }

    fun getResultPart1() {
        val root = Directory(name = "/")
        var currentWorkingDirectory = root

        getInputAsSequence().drop(1).map {
            parseTerminalOutput(it)
        }.forEach {
            currentWorkingDirectory = processTerminalOutput(it, currentWorkingDirectory)
        }
        recursiveVisit(root, { dir -> dir.getTotalSize() < 100000L }, 0L, { it.getTotalSize() }) { left, right ->
            left + right
        }
    }

    fun getResultPart2() {
        val root = Directory(name = "/")
        var currentWorkingDirectory = root

        getInputAsSequence().drop(1).map {
            parseTerminalOutput(it)
        }.forEach {
            currentWorkingDirectory = processTerminalOutput(it, currentWorkingDirectory)
        }

        val totalDiskSpace = 70000000L
        val freeSpaceNeeded = 30000000L
        val totalSpaceUsed = root.getTotalSize()
        val unusedSpace = totalDiskSpace - totalSpaceUsed
        val spaceToFree = freeSpaceNeeded - unusedSpace

        recursiveVisit(root, { dir -> dir.getTotalSize() >= spaceToFree }, Long.MAX_VALUE, { it.getTotalSize() }) { left, right ->
            minOf(left, right)
        }
    }

    private fun processTerminalOutput(
        terminalOutput: TerminalOutput,
        currentWorkingDirectory: Directory
    ): Directory {
        when (terminalOutput) {
            is ChangeDirectory -> {
                return if (terminalOutput.target == "..") {
                    currentWorkingDirectory.parent!!
                } else {
                    currentWorkingDirectory.subdirectories.first { subdir -> subdir.name == terminalOutput.target }
                }
            }

            is ListDirectory -> {}
            is Directory -> {
                terminalOutput.parent = currentWorkingDirectory
                currentWorkingDirectory.subdirectories.add(terminalOutput)
            }

            is File -> currentWorkingDirectory.files.add(terminalOutput)
        }

        return currentWorkingDirectory
    }

    private fun parseTerminalOutput(line: String): TerminalOutput {
        if (line.startsWith("$")) {
            return parseCommand(line.substringAfter("$ "))
        }
        return parseRegularOutput(line)
    }

    private fun parseCommand(commandLine: String) =
        if (commandLine.startsWith("cd")) {
            val parts = commandLine.split(" ")
            ChangeDirectory(parts[1])
        } else {
            ListDirectory
        }

    private fun parseRegularOutput(line: String): DirectoryOrFileOutput {
        return if (line.startsWith("dir")) {
            Directory(line.substringAfter("dir "))
        } else {
            val parts = line.split(" ")
            File(name = parts[1], size = parts[0].toLong())
        }
    }

    private fun <T> recursiveVisit(
        dir: Directory,
        filter: (Directory) -> Boolean,
        defaultValue: T,
        operation: (Directory) -> T,
        combinator: (T, T) -> T
    ): T {
        var result = if (filter.invoke(dir)) operation.invoke(dir) else defaultValue
        dir.subdirectories.forEach { subdir ->
            result = combinator.invoke(result, recursiveVisit(subdir, filter, defaultValue, operation, combinator))
        }
        return result
    }
}
