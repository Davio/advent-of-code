package com.github.davio.aoc.y2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * See [Advent of Code 2022 Day 1](https://adventofcode.com/2022/day/6#part2])
 */
class Day6Test : StringSpec({

    "part 1" {
        Day6(1).part1() shouldBe "7"
        Day6(2).part1() shouldBe "5"
        Day6(3).part1() shouldBe "6"
        Day6(4).part1() shouldBe "10"
        Day6(5).part1() shouldBe "11"
    }

    "part 2" {
        Day6(1).part2() shouldBe "19"
        Day6(2).part2() shouldBe "23"
        Day6(3).part2() shouldBe "23"
        Day6(4).part2() shouldBe "29"
        Day6(5).part2() shouldBe "26"
    }
})
