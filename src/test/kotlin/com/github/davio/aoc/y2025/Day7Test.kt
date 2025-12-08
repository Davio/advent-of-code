package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test :
    StringSpec({

        "part 1 example" {
            Day7(1).part1() shouldBe 21L
        }

        "part 1 input" {
            Day7().part1() shouldBe 1546L
        }

        "part 2 example" {
            Day7(1).part2() shouldBe 40L
        }

        "part 2 input" {
            Day7().part2() shouldBe 11643736116335L
        }
    })
