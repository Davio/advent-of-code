package com.github.davio.aoc.y2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test :
    StringSpec({

        "part 1" {
            Day3(1).part1() shouldBe 161
        }

        "part 2" {
            Day3(2).part2() shouldBe 48
        }
    })
