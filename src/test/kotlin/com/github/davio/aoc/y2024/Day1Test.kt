package com.github.davio.aoc.y2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day1Test :
    StringSpec({

        "part 1" {
            Day1(1).part1() shouldBe 11
        }

        "part 2" {
            Day1(1).part2() shouldBe 31
        }
    })
