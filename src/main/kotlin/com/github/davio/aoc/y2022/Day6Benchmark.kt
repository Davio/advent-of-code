package com.github.davio.aoc.y2022

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class Day6Benchmark {

    @Benchmark
    fun part2() {
        Day6().part2()
    }
}
