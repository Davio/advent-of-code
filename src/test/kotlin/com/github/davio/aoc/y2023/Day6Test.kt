package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day6Test : StringSpec({

    "part 1" {
        Day6.part1() shouldBe 288
    }

    "part 2" {
        Day6.part2() shouldBe 71503
    }
})
