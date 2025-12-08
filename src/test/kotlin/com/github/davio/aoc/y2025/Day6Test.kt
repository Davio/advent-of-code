package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day6Test :
    StringSpec({

        "part 1 example" {
            Day6(1).part1() shouldBe 4277556L
        }

        "part 1 input" {
            Day6().part1() shouldBe 4693159084994L
        }

        "part 2 example" {
            Day6(1).part2() shouldBe 3263827L
        }

        "part 2 input" {
            Day6().part2() shouldBe 11643736116335L
        }
    })
