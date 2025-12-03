package com.github.davio.aoc.i18n

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class P1Test :
    StringSpec({

        "part 1" {
            P1().part1() shouldBe 31
        }
    })
