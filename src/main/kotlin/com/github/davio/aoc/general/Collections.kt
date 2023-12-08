package com.github.davio.aoc.general

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var mul = 1.toLong()
    for (element in this) {
        mul *= selector(element)
    }
    return mul
}

suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

fun <T> Iterable<T>.permutations(k: Int = count()): Sequence<List<T>> {
    val newList = ArrayList<T>(this.toList())

    if (k == 1) {
        return sequenceOf(newList)
    }

    fun <T> swap(list: MutableList<T>, index1: Int, index2: Int) {
        val temp = list[index1]
        list[index1] = list[index2]
        list[index2] = temp
    }

    return sequence {
        for (i in (0 until k)) {
            yieldAll(newList.permutations(k - 1))

            if ((k - 1) % 2 == 0) {
                swap(newList, i, k - 1)
            } else {
                swap(newList, 0, k - 1)
            }
        }
    }
}
