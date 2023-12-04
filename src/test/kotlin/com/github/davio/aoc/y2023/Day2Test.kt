package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Test : StringSpec({

    "part 1" {
        Day2.part1() shouldBe 8
    }

    "part 2" {
        Day2.part2() shouldBe 2286
    }
})
