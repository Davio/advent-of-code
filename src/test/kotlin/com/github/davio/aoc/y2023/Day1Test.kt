package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/1#part2])
 */
class Day1Test : StringSpec({

    "part 1" {
        Day1(1).part1() shouldBe 142
    }

    "part 2" {
        Day1(2).part2() shouldBe 281
    }
})
