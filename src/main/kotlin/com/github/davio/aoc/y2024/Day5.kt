package com.github.davio.aoc.y2024

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsLines
import com.github.davio.aoc.general.split

class Day5(
    exampleNumber: Int? = null,
) : Day(exampleNumber) {
    private lateinit var pageOrderingRules: List<PageOrderingRule>
    private lateinit var updates: List<List<Int>>

    override fun part1(): Int =
        updates
            .filter {
                it.isValid
            }.sumOf {
                it[it.size / 2]
            }

    private val List<Int>.isValid: Boolean
        get() =
            all {
                isValidPage(this, it)
            }

    private fun isValidPage(
        update: List<Int>,
        page: Int,
    ): Boolean =
        pageOrderingRules.all {
            if (it.earlierPage != page && it.laterPage != page) {
                true
            } else {
                val myIndex = update.indexOf(page)

                if (it.earlierPage == page) {
                    val afterIndex = update.indexOf(it.laterPage)
                    afterIndex < 0 || afterIndex > myIndex
                } else {
                    val beforeIndex = update.indexOf(it.earlierPage)
                    beforeIndex < 0 || myIndex > beforeIndex
                }
            }
        }

    override fun part2(): Int {
        val pageOrderChain = buildPageOrderChain()

        return updates
            .filterNot {
                it.isValid
            }.sumOf {
                val reordered = it.reorder(pageOrderChain)
                reordered[reordered.size / 2]
            }
    }

    private fun buildPageOrderChain(): List<Int> {
        val pageRulesBeforeMap = mutableMapOf<Int, MutableList<Int>>()
        pageOrderingRules.forEach { (earlierPage, laterPage) ->
            pageRulesBeforeMap.getOrPut(earlierPage) { mutableListOf() }.add(laterPage)
        }
        val pageRulesAfterMap = mutableMapOf<Int, MutableList<Int>>()
        pageOrderingRules.forEach { (earlierPage, laterPage) ->
            pageRulesAfterMap.getOrPut(laterPage) { mutableListOf() }.add(earlierPage)
        }

        val pageOrderChain = (pageRulesBeforeMap.keys + pageRulesAfterMap.keys).distinct().toMutableList()
        val pagesWithRules = pageOrderChain.toList()
        println(pageOrderChain)

        while (!pageOrderChain.isValid) {
            pagesWithRules.forEach { page ->
                var originalIndex = pageOrderChain.indexOf(page)
                val lastIndex =
                    pageOrderChain.indexOfFirst { page ->
                        page in (pageRulesBeforeMap[page] ?: emptyList())
                    }

                if (lastIndex != -1) {
                    println("Last index: $lastIndex")
                    pageOrderChain.add(lastIndex, page)
                    if (originalIndex > lastIndex) {
                        pageOrderChain.removeAt(originalIndex + 1)
                    } else {
                        pageOrderChain.removeAt(originalIndex)
                    }
                }

                originalIndex = pageOrderChain.indexOf(page)
                val earliestIndex =
                    pageOrderChain.withIndex().indexOfLast { (index, page) ->
                        index < lastIndex
                        page in (pageRulesAfterMap[page] ?: emptyList())
                    }

                if (earliestIndex != -1) {
                    println("Earliest index: $earliestIndex")
                    pageOrderChain.add(earliestIndex, page)
                    if (originalIndex > lastIndex) {
                        pageOrderChain.removeAt(originalIndex + 1)
                    } else {
                        pageOrderChain.removeAt(originalIndex)
                    }
                }
                println(pageOrderChain)
            }
            println(pageOrderChain)
        }
        return pageOrderChain
    }

    private fun List<Int>.reorder(pageOrderChain: List<Int>): List<Int> =
        this
            .sortedWith { o1, o2 ->
                pageOrderChain.indexOf(o1).compareTo(pageOrderChain.indexOf(o2))
            }

    override fun parseInput() {
        val (section1, section2) = getInputAsLines().split { it.isBlank() }
        pageOrderingRules =
            section1.map {
                val (before, after) = it.split('|').map { it.toInt() }
                PageOrderingRule(before, after)
            }
        updates =
            section2.map {
                it.split(',').map { it.toInt() }
            }
    }

    private data class PageOrderingRule(
        val earlierPage: Int,
        val laterPage: Int,
    )
}
