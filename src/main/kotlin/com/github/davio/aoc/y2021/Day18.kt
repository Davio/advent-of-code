package com.github.davio.aoc.y2021

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.combinations
import com.github.davio.aoc.general.getInputAsList
import com.github.davio.aoc.general.getInputAsSequence
import kotlin.system.measureTimeMillis

fun main() {
    Day18.getResultPart1()
    measureTimeMillis {
        Day18.getResultPart2()
    }.call { println("Took $it ms") }
}

object Day18 {

    /*
--- Day 18: Snailfish ---

You descend into the ocean trench and encounter some snailfish. They say they saw the sleigh keys! They'll even tell you which direction the keys went if you help one of the smaller snailfish with his math homework.

Snailfish numbers aren't like regular numbers. Instead, every snailfish number is a pair - an ordered list of two elements. Each element of the pair can be either a regular number or another pair.

Pairs are written as [x,y], where x and y are the elements within the pair. Here are some example snailfish numbers, one snailfish number per line:

[1,2]
[[1,2],3]
[9,[8,7]]
[[1,9],[8,5]]
[[[[1,2],[3,4]],[[5,6],[7,8]]],9]
[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]
[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]

This snailfish homework is about addition. To add two snailfish numbers, form a pair from the left and right parameters of the addition operator. For example, [1,2] + [[3,4],5] becomes [[1,2],[[3,4],5]].

There's only one problem: snailfish numbers must always be reduced, and the process of adding two snailfish numbers can result in snailfish numbers that need to be reduced.

To reduce a snailfish number, you must repeatedly do the first action in this list that applies to the snailfish number:

    If any pair is nested inside four pairs, the leftmost such pair explodes.
    If any regular number is 10 or greater, the leftmost such regular number splits.

Once no action in the above list applies, the snailfish number is reduced.

During reduction, at most one action applies, after which the process returns to the top of the list of actions. For example, if split produces a pair that meets the explode criteria, that pair explodes before other splits occur.

To explode a pair, the pair's left value is added to the first regular number to the left of the exploding pair (if any), and the pair's right value is added to the first regular number to the right of the exploding pair (if any). Exploding pairs will always consist of two regular numbers. Then, the entire exploding pair is replaced with the regular number 0.

Here are some examples of a single explode action:

    [[[[[9,8],1],2],3],4] becomes [[[[0,9],2],3],4] (the 9 has no regular number to its left, so it is not added to any regular number).
    [7,[6,[5,[4,[3,2]]]]] becomes [7,[6,[5,[7,0]]]] (the 2 has no regular number to its right, and so it is not added to any regular number).
    [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3].
    [[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]] becomes [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]] (the pair [3,2] is unaffected because the pair [7,3] is further to the left; [3,2] would explode on the next action).
    [[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]] becomes [[3,[2,[8,0]]],[9,[5,[7,0]]]].

To split a regular number, replace it with a pair; the left element of the pair should be the regular number divided by two and rounded down, while the right element of the pair should be the regular number divided by two and rounded up. For example, 10 becomes [5,5], 11 becomes [5,6], 12 becomes [6,6], and so on.

Here is the process of finding the reduced result of [[[[4,3],4],4],[7,[[8,4],9]]] + [1,1]:

after addition: [[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]
after explode:  [[[[0,7],4],[7,[[8,4],9]]],[1,1]]
after explode:  [[[[0,7],4],[15,[0,13]]],[1,1]]
after split:    [[[[0,7],4],[[7,8],[0,13]]],[1,1]]
after split:    [[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]
after explode:  [[[[0,7],4],[[7,8],[6,0]]],[8,1]]

Once no reduce actions apply, the snailfish number that remains is the actual result of the addition operation: [[[[0,7],4],[[7,8],[6,0]]],[8,1]].

The homework assignment involves adding up a list of snailfish numbers (your puzzle input). The snailfish numbers are each listed on a separate line. Add the first snailfish number and the second, then add that result and the third, then add that result and the fourth, and so on until all numbers in the list have been used once.

For example, the final sum of this list is [[[[1,1],[2,2]],[3,3]],[4,4]]:

[1,1]
[2,2]
[3,3]
[4,4]

The final sum of this list is [[[[3,0],[5,3]],[4,4]],[5,5]]:

[1,1]
[2,2]
[3,3]
[4,4]
[5,5]

The final sum of this list is [[[[5,0],[7,4]],[5,5]],[6,6]]:

[1,1]
[2,2]
[3,3]
[4,4]
[5,5]
[6,6]

Here's a slightly larger example:

[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
[7,[5,[[3,8],[1,4]]]]
[[2,[2,2]],[8,[8,1]]]
[2,9]
[1,[[[9,3],9],[[9,0],[0,7]]]]
[[[5,[7,4]],7],1]
[[[[4,2],2],6],[8,7]]

The final sum [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]] is found after adding up the above snailfish numbers:

  [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
+ [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
= [[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]

  [[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]
+ [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
= [[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]

  [[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]
+ [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
= [[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]

  [[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]
+ [7,[5,[[3,8],[1,4]]]]
= [[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]

  [[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]
+ [[2,[2,2]],[8,[8,1]]]
= [[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]

  [[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]
+ [2,9]
= [[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]

  [[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]
+ [1,[[[9,3],9],[[9,0],[0,7]]]]
= [[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]

  [[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]
+ [[[5,[7,4]],7],1]
= [[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]

  [[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]
+ [[[[4,2],2],6],[8,7]]
= [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]

To check whether it's the right answer, the snailfish teacher only checks the magnitude of the final sum. The magnitude of a pair is 3 times the magnitude of its left element plus 2 times the magnitude of its right element. The magnitude of a regular number is just that number.

For example, the magnitude of [9,1] is 3*9 + 2*1 = 29; the magnitude of [1,9] is 3*1 + 2*9 = 21. Magnitude calculations are recursive: the magnitude of [[9,1],[1,9]] is 3*29 + 2*21 = 129.

Here are a few more magnitude examples:

    [[1,2],[[3,4],5]] becomes 143.
    [[[[0,7],4],[[7,8],[6,0]]],[8,1]] becomes 1384.
    [[[[1,1],[2,2]],[3,3]],[4,4]] becomes 445.
    [[[[3,0],[5,3]],[4,4]],[5,5]] becomes 791.
    [[[[5,0],[7,4]],[5,5]],[6,6]] becomes 1137.
    [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]] becomes 3488.

So, given this example homework assignment:

[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]

The final sum is:

[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]

The magnitude of this final sum is 4140.

Add up all of the snailfish numbers from the homework assignment in the order they appear. What is the magnitude of the final sum?
    */

    fun getResultPart1() {
        val finalSum = getInputAsSequence().map { line ->
            parseSnailfishPair(line)
        }.reduce { acc, snailfishNumber ->
            println("  $acc")
            println("+ $snailfishNumber")
            val newNumber = acc + snailfishNumber
            newNumber.reduce()
            println("= $newNumber")
            println()
            newNumber
        }

        println(finalSum.getMagnitude())
    }

    private fun parseSnailfishPair(input: String, parent: SnailfishPair? = null): SnailfishPair {
        var openingChars = 0
        var closingChars = 0

        input.forEachIndexed { index, c ->
            if (c == '[') openingChars++
            if (c == ']') closingChars++
            if (c == ',' && openingChars - closingChars == 1) {
                val leftSide = input.substring(1, index)
                val rightSide = input.substring(index + 1, input.lastIndex)
                val snailfishPair = SnailfishPair(parent)
                snailfishPair.left = parsePart(leftSide, snailfishPair)
                snailfishPair.right = parsePart(rightSide, snailfishPair)
                return snailfishPair
            }
        }

        return SnailfishPair()
    }

    private fun parsePart(part: String, parent: SnailfishPair): SnailfishNumber {
        return if (part.startsWith("[")) {
            parseSnailfishPair(part, parent)
        } else {
            RegularNumber(part.toInt(), parent)
        }
    }

    /*
--- Part Two ---

You notice a second question on the back of the homework assignment:

What is the largest magnitude you can get from adding only two of the snailfish numbers?

Note that snailfish addition is not commutative - that is, x + y and y + x can produce different results.

Again considering the last example homework assignment above:

[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]

The largest magnitude of the sum of any two snailfish numbers in this list is 3993. This is the magnitude of [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]] + [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]], which reduces to [[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]].

What is the largest magnitude of any sum of two different snailfish numbers from the homework assignment?
    */

    fun getResultPart2() {
        val numbers = getInputAsList().map { line ->
            parseSnailfishPair(line)
        }

        numbers.combinations(2).flatMap {
            println("${it[0]} + ${it[1]}")
            println("${it[1]} + ${it[0]}")
            sequenceOf(it[0] + it[1], it[1] + it[0])
        }.maxOf {
            val reduced = it.reduce()
            println("Reduced sum: $reduced")
            val magnitude = reduced.getMagnitude()
            println("$reduced $magnitude")
            magnitude
        }.call { println(it) }
    }

    private sealed class SnailfishNumber(var parent: SnailfishPair? = null) {

        operator fun plus(other: SnailfishNumber): SnailfishPair {
            val snailfishPair = SnailfishPair()
            this.parent = snailfishPair
            other.parent = snailfishPair
            snailfishPair.left = this
            snailfishPair.right = other
            return snailfishPair
        }

        abstract fun copy(): SnailfishNumber

        abstract fun getValue(): String

        abstract fun getMagnitude(): Int
    }

    private class SnailfishPair(parent: SnailfishPair? = null) : SnailfishNumber(parent) {

        lateinit var left: SnailfishNumber
        lateinit var right: SnailfishNumber

        fun reduce(): SnailfishPair {
            val copy = this.copy() as SnailfishPair
            reduceCopy(copy)
            return copy
        }

        private fun reduceCopy(snailfishPair: SnailfishPair) {
            val fourthNestedPair = FourthNestedPair(snailfishPair)
            fourthNestedPair.findFourthNestedPair()
            if (fourthNestedPair.pair != null) {
                snailfishPair.explode(fourthNestedPair)
                reduceCopy(snailfishPair)
                return
            }

            val regularNumberToSplit = findRegularNumberToSplit(snailfishPair)
            if (regularNumberToSplit != null) {
                snailfishPair.split(regularNumberToSplit)
                reduceCopy(snailfishPair)
            }
        }

        override fun copy(): SnailfishNumber {
            val copy = SnailfishPair()
            copy.left = this.left.copy()
            copy.left.parent = copy
            copy.right = this.right.copy()
            copy.right.parent = copy
            return copy
        }

        private fun explode(fourthNestedPair: FourthNestedPair) {
            fourthNestedPair.regularNumberLeft?.call {
                it.value += (fourthNestedPair.pair!!.left as RegularNumber).value
            }
            fourthNestedPair.regularNumberRight?.call {
                it.value += (fourthNestedPair.pair!!.right as RegularNumber).value
            }

            val parent = fourthNestedPair.pair!!.parent
            val zeroNumber = RegularNumber(0, parent)
            if (parent!!.left === fourthNestedPair.pair) {
                parent!!.left = zeroNumber
            } else {
                parent!!.right = zeroNumber
            }
        }

        private fun findRegularNumberToSplit(snailfishPair: SnailfishPair = this): RegularNumber? {
            if (snailfishPair.left is RegularNumber && (snailfishPair.left as RegularNumber).value >= 10) {
                return snailfishPair.left as RegularNumber
            }

            var regularNumberToSplit: RegularNumber? = null
            if (snailfishPair.left is SnailfishPair) {
                regularNumberToSplit = findRegularNumberToSplit(snailfishPair.left as SnailfishPair)
                if (regularNumberToSplit != null) {
                    return regularNumberToSplit
                }
            }

            if (snailfishPair.right is RegularNumber && (snailfishPair.right as RegularNumber).value >= 10) {
                return snailfishPair.right as RegularNumber
            }

            if (snailfishPair.right is SnailfishPair) {
                regularNumberToSplit = findRegularNumberToSplit(snailfishPair.right as SnailfishPair)
            }

            return regularNumberToSplit
        }

        private fun split(regularNumberToSplit: RegularNumber) {
            val newPair = SnailfishPair(regularNumberToSplit.parent)
            val value = regularNumberToSplit.value
            newPair.left = RegularNumber(value / 2, newPair)
            newPair.right = RegularNumber(if (value % 2 == 0) value / 2 else value / 2 + 1, newPair)

            if (regularNumberToSplit.parent!!.left == regularNumberToSplit) {
                regularNumberToSplit.parent!!.left = newPair
            } else {
                regularNumberToSplit.parent!!.right = newPair
            }
        }

        override fun getValue(): String {
            return "pair"
        }

        override fun getMagnitude() = 3 * left.getMagnitude() + 2 * right.getMagnitude()

        override fun toString(): String {
            return "[$left,$right]"
        }
    }

    private class RegularNumber(var value: Int, parent: SnailfishPair?) : SnailfishNumber(parent) {

        override fun copy(): SnailfishNumber {
            return RegularNumber(this.value, null)
        }

        override fun getValue(): String {
            return value.toString()
        }

        override fun getMagnitude() = value

        override fun toString(): String {
            return value.toString()
        }
    }

    private data class FourthNestedPair(
        val root: SnailfishPair,
        var pair: SnailfishPair? = null,
        var regularNumberLeft: RegularNumber? = null,
        var regularNumberRight: RegularNumber? = null
    ) {

        fun findFourthNestedPair(snailfishNumber: SnailfishNumber = root, depth: Int = 0) {
            if (snailfishNumber is RegularNumber && pair == null) {
                regularNumberLeft = snailfishNumber
                return
            }

            if (snailfishNumber is SnailfishPair && pair == null && depth == 4) {
                pair = snailfishNumber
                return
            }

            if (snailfishNumber is RegularNumber && regularNumberRight == null) {
                regularNumberRight = snailfishNumber
                return
            }

            if (snailfishNumber is SnailfishPair) {
                findFourthNestedPair(snailfishNumber.left, depth + 1)
                findFourthNestedPair(snailfishNumber.right, depth + 1)
            }
        }
    }

    private fun traversePreOrder(snailfishPair: SnailfishPair): String {
        val sb = StringBuilder()
        sb.append(snailfishPair.getValue())
        val pointerRight = "└──"
        val pointerLeft = "├──"
        traverseNodes(sb, "", pointerLeft, snailfishPair.left, true)
        traverseNodes(sb, "", pointerRight, snailfishPair.right, false)
        return sb.toString()
    }


    private fun traverseNodes(
        sb: StringBuilder, padding: String?, pointer: String?, node: SnailfishNumber?, hasRightSibling: Boolean
    ) {
        if (node != null) {
            sb.appendLine()
            sb.append(padding)
            sb.append(pointer)
            sb.append(node.getValue())
            sb.append(" ").append(padding!!.length / 3 + 1)
            val paddingBuilder = StringBuilder(padding)
            if (hasRightSibling) {
                paddingBuilder.append("│  ")
            } else {
                paddingBuilder.append("   ")
            }
            val paddingForBoth = paddingBuilder.toString()
            val pointerRight = "└──"
            val pointerLeft = if (node is SnailfishPair) "├──" else "└──"
            if (node is SnailfishPair) {
                traverseNodes(sb, paddingForBoth, pointerLeft, node.left, true)
                traverseNodes(sb, paddingForBoth, pointerRight, node.right, false)
            }
        }
    }
}