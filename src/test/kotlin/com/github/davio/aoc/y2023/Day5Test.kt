package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day5Test : StringSpec({

    "part 1" {
        Day5.part1() shouldBe 35
    }

    "part 2" {
        Day5.part2() shouldBe 46
    }
})
