package com.github.davio.aoc.y2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Test :
    StringSpec({

        "part 1" {
            Day2(1).part1() shouldBe 2
        }

        "part 2" {
            Day2(1).part2() shouldBe 4
        }

        "part 2 extra" {
            Day2(2).part2() shouldBe 1
        }
    })
