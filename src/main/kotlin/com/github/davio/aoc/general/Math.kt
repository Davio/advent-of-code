package com.github.davio.aoc.general

import kotlin.math.*

val Int.squared get() = this * this
val Long.squared get() = this * this
val Int.sqrt get() = sqrt(this.toDouble())
val Long.sqrt get() = sqrt(this.toDouble())
fun Double.roundUp() = ceil(this).toLong()
fun Double.roundDown() = this.toLong()

fun gcd(n1: Int, n2: Int): Int {
    return when {
        n1 == 0 -> n2
        n2 == 0 -> n1
        else -> {
            val absNumber1 = abs(n1)
            val absNumber2 = abs(n2)
            val smallerValue = min(absNumber1, absNumber2)
            gcd(max(absNumber1, absNumber2) % smallerValue, smallerValue)
        }
    }
}

fun gcd(n1: Long, n2: Long):Long {
    return when {
        n1 == 0L -> n2
        n2 == 0L -> n1
        else -> {
            val absNumber1 = abs(n1)
            val absNumber2 = abs(n2)
            val smallerValue = min(absNumber1, absNumber2)
            gcd(max(absNumber1, absNumber2) % smallerValue, smallerValue)
        }
    }
}

fun lcm(n1: Int, n2: Int): Int {
    return if (n1 == 0 || n2 == 0) 0 else {
        val gcd = gcd(n1, n2)
        abs(n1 * n2) / gcd
    }
}

fun lcm(n1: Long, n2: Long): Long {
    return if (n1 == 0L || n2 == 0L) 0L else {
        val gcd = gcd(n1, n2)
        abs(n1 * n2) / gcd
    }
}
