package com.github.davio.aoc.infi

import kotlin.math.ceil
import kotlin.math.sqrt

fun main() {
    Infi().getResult()
}

/**
 * See [Infi 2020](https://aoc.infi.nl/2020)
 */
class Infi {
    fun getResult() {
        val perContinent =
            sequenceOf(
                4_541_508_876L,
                1_340_843_814L,
                747_798_502L,
                430_872_475L,
                368_974_632L,
                42_720_944L,
            )

        (perContinent.map { getLength(it) }.sum() * 8L).apply { println(this) }
    }

    // ABC-formule = (x1,2 = -b +- Sqrt(b2 - 4ac)) / 2a
    private fun getLength(inhabitants: Long): Int = ceil(((2.0 + sqrt(4.0 + 28.0 * inhabitants)) / 14.0)).toInt()
}
