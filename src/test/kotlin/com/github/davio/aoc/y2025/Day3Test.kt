package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test :
    StringSpec({

        "part 1" {
            Day3(1).part1() shouldBe 357
        }

        "part 2" {
            Day3(1).part2() shouldBe 3121910778619L
        }
    })
