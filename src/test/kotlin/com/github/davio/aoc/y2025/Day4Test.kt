package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day4Test :
    StringSpec({

        "part 1" {
            Day4(1).part1() shouldBe 13
        }

        "part 2" {
            Day4(1).part2() shouldBe 43
        }
    })
