package com.github.davio.aoc.i18n

import com.github.davio.aoc.general.Puzzle
import com.github.davio.aoc.general.getInputAsLineSequence

class P3(
    exampleNumber: Int? = null,
) : Puzzle(exampleNumber) {
    private val passwordRequirements: List<(String) -> Boolean> =
        listOf(
            { it.length in (4..12) },
            { it.any { c -> c.isDigit() } },
            { it.any { c -> c.isUpperCase() } },
            { it.any { c -> c.isLowerCase() } },
            { it.any { c -> c.code > 0x7f } },
        )

    override fun part1(): Int = getInputAsLineSequence().count { it.isValidPassword() }

    private fun String.isValidPassword() =
        passwordRequirements.all { req ->
            req(this)
        }
}
