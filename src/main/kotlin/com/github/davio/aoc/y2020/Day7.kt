package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsList

fun main() {
    Day7.getResult()
}

object Day7 {

    /*
     * --- Day 7: Handy Haversacks ---

You land at the regional airport in time for your next flight.
* In fact, it looks like you'll even have time to grab some food: all flights are currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their contents;
* bags must be color-coded and must contain specific quantities of other color-coded bags.
* Apparently, nobody responsible for these regulations considered how long they would take to enforce!

For example, consider the following rules:

light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.

These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty,
* every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other bag,
* how many different bag colors would be valid for the outermost bag?
* (In other words: how many colors can, eventually, contain at least one shiny gold bag?)

In the above rules, the following options would be available to you:

    A bright white bag, which can hold your shiny gold bag directly.
    A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
    A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
    A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.

So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make sure you get all of it.)
*
* --- Part Two ---

It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous number of bags you need to buy!

Consider again your shiny gold bag and the rules from the above example:

    faded blue bags contain 0 other bags.
    dotted black bags contain 0 other bags.
    vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
    dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.

So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it)
* plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!

Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags,
* even if the nesting becomes topologically impractical!

Here's another example:

shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.

In this example, a single shiny gold bag must contain 126 other bags.

How many individual bags are required inside your single shiny gold bag?
     */

    fun getResult() {
        val lines = getInputAsList()
        val bags = HashMap<String, Bag>(lines.size)

        lines.forEach { line ->
            Bag.parse(line, bags)
        }

        val myColor = "shiny gold"
        bags.count { it.value.canContain(Bag(myColor)) }.call { println(it) }
        bags[myColor]!!.getNumberOfIndividualBags().call { println(it) }
    }

    data class Bag(private val color: String) {

        private val otherBags: MutableMap<Bag, Int> = hashMapOf()

        fun addOtherBag(otherBag: Pair<Bag, Int>) {
            otherBags += otherBag
        }

        fun canContain(bag: Bag): Boolean {
            if (otherBags.isEmpty()) {
                return false
            }

            val otherBagCount = otherBags[bag]
            if (otherBagCount != null && otherBagCount > 0) {
                return true
            }

            return otherBags.keys.any { it.canContain(bag) }
        }

        fun getNumberOfIndividualBags(): Int {
            if (otherBags.isEmpty()) {
                return 0
            }

            return otherBags.entries.sumBy { entry ->
                entry.value + entry.value * entry.key.getNumberOfIndividualBags()
            }
        }

        companion object {

            private val noOtherBagsRegex = Regex("(.+) bags contain no other bags\\.")
            private val otherBagsRegex = Regex("(.+) bags contain .+\\.")
            private val otherBagsPerBagRegex = Regex("(\\d+) ([a-z ]+) bags?")

            fun parse(rule: String, currentBags: MutableMap<String, Bag>) {
                val color: String
                val noOtherBagsMatcher = noOtherBagsRegex.matchEntire(rule)

                if (noOtherBagsMatcher != null) {
                    color = noOtherBagsMatcher.groups[1]!!.value
                    currentBags[color] = Bag(color)
                    return
                }

                val otherBagsMatcher = otherBagsRegex.matchEntire(rule)!!
                color = otherBagsMatcher.groups[1]!!.value
                val bag = getOrCreateBag(currentBags, color)
                otherBagsPerBagRegex.findAll(rule)
                    .forEach {
                        val otherBag = getOrCreateBag(currentBags, it.groupValues[2])
                        bag.addOtherBag(Pair(otherBag, it.groupValues[1].toInt()))
                    }
                currentBags[color] = bag
            }

            private fun getOrCreateBag(currentBags: MutableMap<String, Bag>, otherBagColor: String) =
                currentBags.computeIfAbsent(otherBagColor) {
                    Bag(otherBagColor)
                }
        }
    }
}