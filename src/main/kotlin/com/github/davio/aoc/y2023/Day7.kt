package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.y2023.Day7.Card.Companion.toCard
import com.github.davio.aoc.y2023.Day7.Hand.Companion.toHand

/**
 * See [Advent of Code 2023 Day 6](https://adventofcode.com/2023/day/6#part2])
 */
class Day7(exampleNumber: Int? = null) : Day(exampleNumber) {

    private val input = getInputAsList()
    private val hands = input.map { line ->
        val (handPart, bidPart) = line.split(' ')
        val hand = handPart.toHand()
        HandWithBid(hand, bidPart.toInt())
    }

    override fun part1(): Long {
        return hands.sortedBy { it.hand }.withIndex().sumOf { (index, handWithBid) ->
            println("${handWithBid.hand} ${handWithBid.hand.handRank}")
            println("${handWithBid.bid} * ${index + 1} = ${handWithBid.bid * index + 1}")
            handWithBid.bid.toLong() * (index + 1)
        }
    }

    private enum class Rank {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }

    private enum class Card(val symbol: Char) {
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
        JACK('J'),
        QUEEN('Q'),
        KING('K'),
        ACE('A');

        companion object {
            fun Char.toCard() = enumValues<Card>().first { it.symbol == this }
        }
    }

    private data class Hand(val cards: MutableList<Card>) : Comparable<Hand> {
        val handRank = determineHandRank()

        fun determineHandRank(): Rank {
            val cardsInOrder = cards.sorted()
            return when {
                cardsInOrder.drop(1).all { it == cardsInOrder.first() } -> Rank.FIVE_OF_A_KIND
                cardsInOrder[0] == cardsInOrder[3] || cardsInOrder[1] == cardsInOrder[4] -> Rank.FOUR_OF_A_KIND
                (cardsInOrder[0] == cardsInOrder[2] && cardsInOrder[3] == cardsInOrder[4])
                        || (cardsInOrder[0] == cardsInOrder[1] && cardsInOrder[2] == cardsInOrder[4]) -> Rank.FULL_HOUSE

                cardsInOrder[0] == cardsInOrder[2]
                        || cardsInOrder[1] == cardsInOrder[3]
                        || cardsInOrder[2] == cardsInOrder[4] -> Rank.THREE_OF_A_KIND

                (cardsInOrder[0] == cardsInOrder[1] && cardsInOrder[2] == cardsInOrder[3])
                        || (cardsInOrder[1] == cardsInOrder[2] && cardsInOrder[3] == cardsInOrder[4]) -> Rank.TWO_PAIR

                (0..<cardsInOrder.lastIndex).any { cardsInOrder[it] == cardsInOrder[it + 1] } -> Rank.ONE_PAIR
                else -> Rank.HIGH_CARD
            }
        }

        override fun compareTo(other: Hand): Int {
            var comparison = Comparator.naturalOrder<Rank>().compare(handRank, other.handRank)
            if (comparison != 0) return comparison

            val cardComparator = Comparator.naturalOrder<Card>()

            cards.zip(other.cards).forEach { (myCard, otherCard) ->
                comparison = cardComparator.compare(myCard, otherCard)
                if (comparison != 0) return comparison
            }

            return 0
        }

        override fun toString() = cards.joinToString("") { it.symbol.toString() }

        companion object {
            fun String.toHand() = Hand(this.map { it.toCard() }.toMutableList())
        }
    }

    private data class HandWithBid(val hand: Hand, val bid: Int)
}
