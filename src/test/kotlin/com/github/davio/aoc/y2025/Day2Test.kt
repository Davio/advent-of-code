package com.github.davio.aoc.y2025

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Test :
    StringSpec({

        "part 1" {
            Day2(1).part1() shouldBe 1227775554L
        }

        "part 2" {
            Day2(1).part2() shouldBe 4174379265L
        }
    })
