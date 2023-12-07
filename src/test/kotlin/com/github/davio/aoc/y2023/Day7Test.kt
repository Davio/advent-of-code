package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test : StringSpec({

    "test hand ranks" {
        Day7(1).part1() shouldBe 1
    }

    "part 1" {
        Day7().part1() shouldBe 6440
    }
})
