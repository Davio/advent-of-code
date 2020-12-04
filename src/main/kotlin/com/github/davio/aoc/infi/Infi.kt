package com.github.davio.aoc.infi

import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.sqrt

fun main() {
    Infi().getResult()
}

class Infi {

    /*
     * Bepakt en bezakt in 2020
    In het bijzondere jaar 2020 blijkt de magie van kerstman verbazingwekkend als altijd,
    want ondanks dat normale mensen het een raar jaar vinden, gaat het dit jaar op de Noordpool verbazingwekkend goed.

    De elven hebben van alle kinderen, die lijstje opgestuurd hebben, in record tempo hun voorkeuren uitgezocht en geteld.
    De cadeautjes zijn gekocht en ingepakt en alle pakjes liggen al klaar.
    Ze hoeven alleen nog maar in een zak gedaan te worden, zodat de kerstman deze op kerstavond mee kan nemen.

    En dat is dan wel het enige puntje, waar de elven dit jaar wat hulp bij nodig hebben,
    want ze weten niet hoe groot de zak moet gaan worden.
    De magie van de kerstman en de supersonische snelheid van de arrenslee met de rendieren,
    zorgt er voor dat tijdens het vervoer de pakjes en de zak tweedimensionaal lijken.
    Ook zijn, op magische wijze, alle pakjes even groot op het moment dat ze in de zak komen.
    Tevens weten ze, dat voor een optimale verhouding tussen benodigd materiaal om de zak te maken en inhoud van de zak deze
    tijdens het vervoer de vorm moet krijgen van een Regelmatige achthoek.

    Echter omdat er alleen maar gehele pakjes in de zak kunnen, hebben de elven niets aan de bekende formules
    voor het berekenen van de oppervlakte en dus hebben ze om hulp gevraagd om te helpen met de berekeningen.
    Vereisten:

    De zak moet met een platte kant op de bodem van de slee komen te staan.
    Alle acht de zijden van de zak moeten precies even lang zijn
    De schuine zijden van de zak staan onder een hoek van 135°
    Ieder 1x1 vakje binnen de zak is een plek voor een pakje

    Legenda

    ‾: De bodem
    / of \: een schuine zijde
    |: Een zijkant
    _: De bovenkant
    .: Ruimte voor één pakje

    Achthoek met zijden van lengte: 2

       __
      /..\
     /....\
    |......|
    |......|
     \..../
      \../
       ¯¯

    Het aantal pakjes dat in deze zak passen is:24
    Achthoek met zijden van lengte: 3

        ___
       /...\
      /.....\
     /.......\
    |.........|
    |.........|
    |.........|
     \......./
      \...../
       \.../
        ¯¯¯

    Het aantal pakjes dat in deze zak passen is: 57
    Extra voorbeelden

    In een zak met zijde van lengte 1 passen: 5 pakjes
    In een zak met zijde van lengte 4 passen: 104 pakjes
    In een zak met zijde van lengte 10 passen: 680 pakjes
    In een zak met zijde van lengte 25 passen: 4.325 pakjes

    In Nederland wonen momenteel 17.490.031 inwoners.
    De elven willen graag alle pakjes voor Nederland in één zak stoppen.

    Wat is de minimale lengte van een zijde voor de zak die zak?
     */

    fun getResult() {
        // 7l*l - 2l - 17.490.031 = 0
        val solutions = abcFormule(7, 2, -17490031)
        val minLength = ceil(max(solutions.first, solutions.second)).toInt()
        println(minLength)
    }

    private fun abcFormule(a: Int, b: Int, c: Int): Pair<Double, Double> {
        // ABC-formule =  (x1,2 = -b +- Sqrt(b2 - 4ac)) / 2a
        val discriminant = b * b - 4.0 * a * c
        return when {
            discriminant > 0 -> Pair(
                (-1 * b + sqrt(discriminant)) / (2 * a),
                (-1 * b - sqrt(discriminant)) / (2 * a)
            )
            discriminant == 0.0 -> Pair((-1.0 * b) / (2 * a), Double.NaN)
            else -> Pair(Double.NaN, Double.NaN)
        }
    }
}