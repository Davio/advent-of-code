package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence

fun main() {
    Day2.getResultPart1()
    Day2.getResultPart2()
}

object Day2 : Day() {

    /*
     * --- Day 2: Password Philosophy ---

    Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via toboggan.

    The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers; we can't log in!"
    You ask if you can take a look.

    Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the
    Official Toboggan Corporate Policy that was in effect when they were chosen.

    To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted database)
    and the corporate policy when that password was set.

    For example, suppose you have the following list:

    1-3 a: abcde
    1-3 b: cdefg
    2-9 c: ccccccccc

    Each line gives the password policy and then the password.
    The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid.
    For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

    In the above example, 2 passwords are valid.
    The middle password, cdefg, is not; it contains no instances of b, but needs at least 1.
    The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

    How many passwords are valid according to their policies?
    *
    * --- Part Two ---

    While it appears you validated the passwords correctly, they don't seem to be what the
    Official Toboggan Corporate Authentication System is expecting.

    The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental
    place down the street! The Official Toboggan Corporate Policy actually works a little differently.

    Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on.
    (Be careful; Toboggan Corporate Policies have no concept of "index zero"!)
    Exactly one of these positions must contain the given letter.
    Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

    Given the same example list from above:

    1-3 a: abcde is valid: position 1 contains a and position 3 does not.
    1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
    2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.

    How many passwords are valid according to the new interpretation of the policies?
     */

    fun getResultPart1() {
        getInputAsSequence()
            .map { getLineParts(it) }
            .count { isResultPart1Valid(it) }
            .call { println(it) }
    }

    private fun getLineParts(line: String): Pair<String, String> {
        val parts = splitLine(line)
        val policy = parts[0]
        val password = parts[1].trim()
        return Pair(policy, password)
    }

    private fun splitLine(line: String) = line.split(":")

    private fun isResultPart1Valid(parts: Pair<String, String>): Boolean {
        return PasswordPolicy.parse(parts.first).checkPart1(parts.second)
    }

    fun getResultPart2() {
        getInputAsSequence()
            .map { getLineParts(it) }
            .count { isResultPart2Valid(it) }
            .call { println(it) }
    }

    private fun isResultPart2Valid(parts: Pair<String, String>): Boolean {
        return PasswordPolicy.parse(parts.first).checkPart2(parts.second)
    }

    class PasswordPolicy private constructor(
        private val n1: Int,
        private val n2: Int,
        private val char: Char
    ) {
        fun checkPart1(password: String) = password.count { it == char } in n1..n2

        fun checkPart2(password: String) = (password[n1 - 1] == char) xor (password[n2 - 1] == char)

        companion object {

            fun parse(policy: String): PasswordPolicy {
                val policyParts = policy.split(" ")
                val numberParts = policyParts[0].split("-")

                return PasswordPolicy(
                    numberParts[0].toInt(),
                    numberParts[1].toInt(),
                    policyParts[1][0]
                )
            }
        }
    }
}
