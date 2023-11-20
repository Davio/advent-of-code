package com.github.davio.aoc.y2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/1#part2])
 */
class Day1Test : StringSpec({

    "part 1" {
        Day1.part1() shouldBe "24000"
    }

    "part 2" {
        Day1.part2() shouldBe "45000"
    }
})
