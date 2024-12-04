package com.github.davio.aoc.y2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day4Test :
    StringSpec({

        "part 1" {
            Day4(1).part1() shouldBe 18
        }

        "part 2" {
            Day4(1).part2() shouldBe 9
        }
    })
