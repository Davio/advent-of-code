package com.github.davio.aoc.i18n

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class P2Test :
    StringSpec({

        "part 1" {
            P2().part1() shouldBe "2019-06-05T12:15:00+00:00"
        }
    })
