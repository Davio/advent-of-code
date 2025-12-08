package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day5Test :
    StringSpec({

        "part 1 example" {
            Day5(1).part1() shouldBe 3
        }

        "part 1 input" {
            Day5().part1() shouldBe 868
        }

        "part 2 example" {
            Day5(1).part2() shouldBe 14uL
        }

        "part 2 input" {
            Day5().part2() shouldBe 354143734113772uL
        }
    })
