package com.github.davio.aoc.y2023

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day10Test :
    StringSpec({

        "part 1 example 1" {
            Day10(1).part1() shouldBe 4
        }

        "part 1 example 2" {
            Day10(2).part1() shouldBe 8
        }

        "part 2 example 3" {
            Day10(3).part2() shouldBe 4
        }

        "part 2 example 4" {
            Day10(4).part2() shouldBe 8
        }
    })
