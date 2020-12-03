package com.github.davio.aoc.general

fun getInputList(number: Int) = ClassLoader.getSystemResourceAsStream("$number.txt")!!.bufferedReader().readLines()

fun getInputSequenceAsStrings(number: Int)= getInputList(number).asSequence()

fun getInputSequenceAsInts(number: Int)= getInputSequenceAsStrings(number).map { Integer.parseInt(it) }

fun getInputAsIntList(number: Int): List<Int> = getInputList(number).map { Integer.parseInt(it) }
