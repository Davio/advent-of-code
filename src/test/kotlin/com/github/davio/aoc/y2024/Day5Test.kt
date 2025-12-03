package com.github.davio.aoc.y2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day5Test :
    StringSpec({

        "part 1" {
            Day5(1).part1() shouldBe 143
        }

        "part 2" {
            Day5(1).part2() shouldBe 123
        }
    })
