package com.github.davio.aoc.i18n

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class P3Test :
    StringSpec({

        "part 1" {
            P3().part1() shouldBe 2
        }
    })
