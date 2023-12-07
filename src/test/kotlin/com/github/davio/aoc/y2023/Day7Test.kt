package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test : StringSpec({

    "test hand ranks" {
        Day7(1).part1() shouldBe 6592
    }

    "edge cases part 2" {
        Day7(1).part2() shouldBe 6839
    }

    "part 1" {
        Day7().part1() shouldBe 6440
    }

    "part 2" {
        Day7().part2() shouldBe 5905
    }
})
