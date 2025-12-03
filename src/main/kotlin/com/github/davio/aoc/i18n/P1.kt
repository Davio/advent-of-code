package com.github.davio.aoc.i18n

import com.github.davio.aoc.general.Puzzle
import com.github.davio.aoc.general.getInputAsLineSequence

private const val SMS_COST = 11
private const val MAX_SMS_SIZE_IN_BYTES = 160
private const val TWEET_COST = 7
private const val MAX_TWEET_SIZE_IN_CHARS = 140
private const val DISCOUNT_RATE = 13

class P1(
    exampleNumber: Int? = null,
) : Puzzle(exampleNumber) {
    override fun part1(): Number =
        getInputAsLineSequence()
            .map { message ->
                val smsSize = message.toByteArray().size
                val isValidAsSms = smsSize <= MAX_SMS_SIZE_IN_BYTES
                val tweetSiize = message.length
                val isValidAsTweet = tweetSiize <= MAX_TWEET_SIZE_IN_CHARS

                var fee = 0
                if (isValidAsSms) fee += SMS_COST
                if (isValidAsTweet) fee += TWEET_COST

                if (isValidAsSms && isValidAsTweet) fee = DISCOUNT_RATE

                fee
            }.sum()
}
