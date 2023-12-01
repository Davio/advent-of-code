package com.github.davio.aoc.y2022

import com.github.davio.aoc.general.Day
import com.github.davio.aoc.general.getInputAsList

/**
 * See [Advent of Code 2022 Day 16](https://adventofcode.com/2022/day/16)
 */
object Day16 : Day() {

    private const val LINE_PATTERN = """Valve ([A-Z]{2}) has flow rate=(\d+); tunnel(?:s?) lead(?:s?) to valve(?:s?) (.+)"""
    private val LINE_REGEX = Regex(LINE_PATTERN)

    private val valveMap: HashMap<String, Valve> = hashMapOf()

    override fun part1(): String {
        getInputAsList()
            .map { parseLine(it) }
            .onEach { println(it) }

        val route = Route()
        val result = getBestRoute(route)
        println(result)
        return result.pressureReleased.toString()
    }

    private fun parseLine(line: String): Valve {
        val (label, flowRate, tunnels) = LINE_REGEX.matchEntire(line)!!.destructured
        val valve = valveMap.getOrPut(label) { Valve(label, flowRate.toInt()) }
        valve.flowRate = flowRate.toInt()

        tunnels.split(", ").forEach {
            valve.addTunnel(it)
        }
        return valve
    }

    data class Valve(val label: String, var flowRate: Int = 0) {
        val tunnels: MutableList<Valve> = mutableListOf()

        fun addTunnel(label: String) {
            val otherValve = valveMap.getOrPut(label) { Valve(label) }
            tunnels.add(otherValve)
        }

        override fun toString(): String = "$label($flowRate): ${tunnels.joinToString { it.label }}"
    }

    private val routeMap: MutableMap<Route, RouteResult> = mutableMapOf()

    private fun getBestRoute(route: Route): RouteResult {
        if (routeMap.contains(route)) {
            return routeMap.getValue(route)
        }

        if (route.timeRemaining < 2) {
            routeMap[route] = RouteResult.EMPTY
            return RouteResult.EMPTY
        }

        val timeRemainingAfterAction = route.timeRemaining - 1
        val openThisValveAction = Action.OpenValve(route.currentValve, timeRemainingAfterAction)

        var combinedResultWithClosedValve: RouteResult? = null

        if (!route.openedValves.contains(route.currentValve) && route.currentValve.flowRate > 0) {
            val resultWithClosedValve = RouteResult(listOf(openThisValveAction), openThisValveAction.getPressureReleased())
            val resultAfterClosingValve = getBestRoute(
                Route(
                    route.currentValve,
                    timeRemainingAfterAction,
                    route.openedValves.toMutableSet() + route.currentValve
                )
            )
            combinedResultWithClosedValve = RouteResult(
                resultWithClosedValve.actions.toMutableList() + resultAfterClosingValve.actions,
                resultWithClosedValve.pressureReleased + resultAfterClosingValve.pressureReleased
            )
        }

        val bestCombinedResultAfterMoving = route.currentValve.tunnels.map { valveMovedTo ->
            val moveAction = Action.MoveToValve(valveMovedTo, timeRemainingAfterAction)
            val bestRouteAfterMoving = getBestRoute(
                Route(
                    valveMovedTo,
                    timeRemainingAfterAction,
                    route.openedValves.toMutableSet()
                )
            )
            RouteResult(
                listOf(moveAction) + bestRouteAfterMoving.actions,
                bestRouteAfterMoving.pressureReleased
            )
        }.maxBy { it.pressureReleased }

        return listOfNotNull(combinedResultWithClosedValve, bestCombinedResultAfterMoving)
            .maxBy { it.pressureReleased }
            .also { routeMap.putIfAbsent(route, it) }
    }

    data class Route(
        var currentValve: Valve = valveMap.getValue("AA"),
        var timeRemaining: Int = 30,
        val openedValves: Set<Valve> = setOf()
    )

    data class RouteResult(
        val actions: List<Action> = mutableListOf(),
        val pressureReleased: Int = 0
    ) {
        override fun toString(): String {
            return actions.joinToString(separator = System.lineSeparator()) {
                "${it.timeRemainingAfterAction + 1} $it"
            } + ", pressure released: $pressureReleased"
        }

        companion object {
            val EMPTY = RouteResult(emptyList(), 0)
        }
    }

    sealed interface Action {
        val valve: Valve
        val timeRemainingAfterAction: Int

        data class OpenValve(override val valve: Valve, override val timeRemainingAfterAction: Int) : Action {
            fun getPressureReleased(): Int = valve.flowRate * timeRemainingAfterAction

            override fun toString(): String = "open valve ${valve.label} with flowRate ${valve.flowRate} " +
                    "for ${valve.flowRate * timeRemainingAfterAction} pressure released"
        }

        data class MoveToValve(override val valve: Valve, override val timeRemainingAfterAction: Int) : Action {
            override fun toString(): String = "move to valve ${valve.label}"
        }
    }
}
