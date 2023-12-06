package com.github.davio.aoc.y2023

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2023 Day 5](https://adventofcode.com/2023/day/5#part2])
 */
object Day5 : Day() {

    private val input = getInputAsList()
    private val seedToSoilMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val soilToFertilizerMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val fertilizerToWaterMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val waterToLightMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val lightToTemperatureMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val temperatureToHumidityMap: MutableList<DestinationSourceRange> = mutableListOf()
    private val humidityToLocationMap: MutableList<DestinationSourceRange> = mutableListOf()

    init {
        var currentMap: MutableList<DestinationSourceRange>? = null

        input.drop(2).forEach { line ->
            when {
                line == "seed-to-soil map:" -> currentMap = seedToSoilMap
                line == "soil-to-fertilizer map:" -> currentMap = soilToFertilizerMap
                line == "fertilizer-to-water map:" -> currentMap = fertilizerToWaterMap
                line == "water-to-light map:" -> currentMap = waterToLightMap
                line == "light-to-temperature map:" -> currentMap = lightToTemperatureMap
                line == "temperature-to-humidity map:" -> currentMap = temperatureToHumidityMap
                line == "humidity-to-location map:" -> currentMap = humidityToLocationMap
                line.isNotBlank() && currentMap != null -> {
                    val parts = line.split(' ')
                    val rangeSize = parts[2].toLong()
                    val destinationRangeStart = parts[0].toLong()
                    val sourceRangeStart = parts[1].toLong()
                    val destinationSourceRange = DestinationSourceRange(destinationRangeStart, sourceRangeStart, rangeSize)
                    currentMap!!.add(destinationSourceRange)
                }
            }
        }

        seedToSoilMap.sort()
        soilToFertilizerMap.sort()
        fertilizerToWaterMap.sort()
        waterToLightMap.sort()
        lightToTemperatureMap.sort()
        temperatureToHumidityMap.sort()
        humidityToLocationMap.sort()
    }

    override fun part1(): Long {
        val seeds = input.first().substringAfter(": ").split(' ').map { it.toLong() }
        return seeds.minOf { getLocation(it) }
    }

    override fun part2(): Long {
        val seedRanges = input.first().substringAfter(": ").split(' ').windowed(2, 2).map {
            val rangeStart = it[0].toLong()
            val rangeSize = it[1].toLong()
            SeedRange(rangeStart, rangeSize)
        }.sortedBy { it.rangeStart }

//        return runBlocking(Dispatchers.Default) {
//            seedRanges.asFlow().map { range ->
//                (range.rangeStart..range.rangeEnd).asFlow().map { seed ->
//                    getLocation(seed)
//                }.reduce { accumulator, value -> min(accumulator, value) }
//            }.reduce { accumulator, value -> min(accumulator, value) }
//        }
        for (location in 0..Long.MAX_VALUE) {
            val seed = getSeed(location)
            if (seedRanges.any { range -> seed in range }) {
                return location
            }
        }

        return -1
    }

    private fun getLocation(seed: Long): Long {
        val soil = getMapping(seed, seedToSoilMap)
        val fertilizer = getMapping(soil, soilToFertilizerMap)
        val water = getMapping(fertilizer, fertilizerToWaterMap)
        val light = getMapping(water, waterToLightMap)
        val temperature = getMapping(light, lightToTemperatureMap)
        val humidity = getMapping(temperature, temperatureToHumidityMap)
        return getMapping(humidity, humidityToLocationMap)
    }

    private fun getSeed(location: Long): Long {
        val humidity = getReverseMapping(location, humidityToLocationMap)
        val temperature = getReverseMapping(humidity, temperatureToHumidityMap)
        val light = getReverseMapping(temperature, lightToTemperatureMap)
        val water = getReverseMapping(light, waterToLightMap)
        val fertilizer = getReverseMapping(water, fertilizerToWaterMap)
        val soil = getReverseMapping(fertilizer, soilToFertilizerMap)
        return getReverseMapping(soil, seedToSoilMap)
    }

    private fun getMapping(source: Long, map: List<DestinationSourceRange>): Long {
        return map.firstNotNullOfOrNull {
            getDestinationNumber(source, it)
        } ?: source
    }

    private fun getReverseMapping(destination: Long, map: List<DestinationSourceRange>): Long {
        return map.firstNotNullOfOrNull {
            getSourceNumber(destination, it)
        } ?: destination
    }

    private fun collapseMaps(sourceMap: List<DestinationSourceRange>, destinationMap: List<DestinationSourceRange>): List<DestinationSourceRange> {

        return emptyList()
    }

    private fun getDestinationNumber(source: Long, destinationSourceRange: DestinationSourceRange): Long? {
        if (source < destinationSourceRange.sourceRangeStart || source > destinationSourceRange.sourceRangeEnd) {
            return null
        }

        val diff = source - destinationSourceRange.sourceRangeStart
        return destinationSourceRange.destinationRangeStart + diff
    }

    private fun getSourceNumber(destination: Long, destinationSourceRange: DestinationSourceRange): Long? {
        if (destination < destinationSourceRange.destinationRangeStart || destination > destinationSourceRange.destinationRangeEnd) {
            return null
        }

        val diff = destination - destinationSourceRange.destinationRangeStart
        return destinationSourceRange.sourceRangeStart + diff
    }

    private data class DestinationSourceRange(
        val destinationRangeStart: Long,
        val sourceRangeStart: Long,
        val rangeSize: Long
    ) : Comparable<DestinationSourceRange> {
        val destinationRangeEnd = destinationRangeStart + rangeSize - 1
        val sourceRangeEnd = sourceRangeStart + rangeSize - 1

        override fun compareTo(other: DestinationSourceRange): Int =
            Comparator.comparingLong<DestinationSourceRange> { it.sourceRangeStart }
                .compare(this, other)
    }

    private data class SeedRange(
        val rangeStart: Long,
        val rangeSize: Long
    ) {
        val rangeEnd = rangeStart + rangeSize - 1

        operator fun contains(seed: Long) = seed in rangeStart..rangeEnd
    }
}
