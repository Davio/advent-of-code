package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test : StringSpec({

    "part 1" {
        Day3.part1() shouldBe 4361
    }

    "part 2" {
        Day3.part2() shouldBe 467835
    }
})
