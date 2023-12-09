package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day8Test : StringSpec({

    "part 1 example 1" {
        Day8(1).part1() shouldBe 2
    }

    "part 1 example 2" {
        Day8(2).part1() shouldBe 6
    }

    "part 2 example 3" {
        Day8(3).part2() shouldBe 6
    }

    "part 2 example 4" {
        Day8(4).part2() shouldBe 20
    }
})
