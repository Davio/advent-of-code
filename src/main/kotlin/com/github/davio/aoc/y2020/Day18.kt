package com.github.davio.aoc.y2020

import com.github.davio.aoc.general.call
import com.github.davio.aoc.general.getInputAsSequence
import java.lang.System.lineSeparator
import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        Day18.getResult()
    }.call { println("$it ms") }
}

object Day18 {

    /*
     * --- Day 18: Operation Order ---

As you look out the window and notice a heavily-forested continent slowly appear over the horizon,
* you are interrupted by the child sitting next to you. They're curious if you could help them with their math homework.

Unfortunately, it seems like this "math" follows different rules than you remember.

The homework (your puzzle input) consists of a series of expressions that consist of
* addition (+), multiplication (*), and parentheses ((...)).
* Just like normal math, parentheses indicate that the expression inside must be evaluated
* before it can be used by the surrounding expression.
* Addition still finds the sum of the numbers on both sides of the operator, and multiplication still finds the product.

However, the rules of operator precedence have changed.
* Rather than evaluating multiplication before addition, the operators have the same precedence,
* and are evaluated left-to-right regardless of the order in which they appear.

For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are as follows:

1 + 2 * 3 + 4 * 5 + 6
  3   * 3 + 4 * 5 + 6
      9   + 4 * 5 + 6
         13   * 5 + 6
             65   + 6
                 71

Parentheses can override this order; for example, here is what happens if parentheses are added to form 1 + (2 * 3) + (4 * (5 + 6)):

1 + (2 * 3) + (4 * (5 + 6))
1 +    6    + (4 * (5 + 6))
     7      + (4 * (5 + 6))
     7      + (4 *   11   )
     7      +     44
            51

Here are a few more examples:

    2 * 3 + (4 * 5) becomes 26.
    5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 437.
    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 12240.
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 13632.

Before you can help with the homework, you need to understand it yourself. Evaluate the expression on each line of the homework;
* what is the sum of the resulting values?
*
* --- Part Two ---

You manage to answer the child's questions and they finish part 1 of their homework,
* but get stuck when they reach the next section: advanced math.

Now, addition and multiplication have different precedence levels,
* but they're not the ones you're familiar with. Instead, addition is evaluated before multiplication.

For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are now as follows:

1 + 2 * 3 + 4 * 5 + 6
  3   * 3 + 4 * 5 + 6
  3   *   7   * 5 + 6
  3   *   7   *  11
     21       *  11
         231

Here are the other examples from above:

    1 + (2 * 3) + (4 * (5 + 6)) still becomes 51.
    2 * 3 + (4 * 5) becomes 46.
    5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 1445.
    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 669060.
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 23340.

What do you get if you add up the results of evaluating the homework problems using these new rules?
     */

    private val input = getInputAsSequence()
    private val outputQueue = arrayListOf<OutputToken>()
    private val operatorStack = Stack<Operator>()

    fun getResult() {
        input.map { line ->
            operatorStack.clear()
            outputQueue.clear()

            val tokens = line.replace("(", "( ").replace(")", " )").split(" ")
            tokens.forEach { parseToken(it) }
            while (!operatorStack.isEmpty()) {
                outputQueue.add(operatorStack.pop())
            }

            val stack = Stack<Long>()
            outputQueue.forEach {
                print("$it ")
                val newValue = when (it) {
                    is Number -> {
                        it.value
                    }
                    else -> {
                        val right = stack.pop()
                        val left = stack.pop()
                        if (it is Plus) {
                            left + right
                        } else {
                            left * right
                        }
                    }
                }
                stack.push(newValue)
            }
            stack.pop().also { res -> print("= $res ${lineSeparator()}") }
        }.sum().call { println(it) }
    }

    private fun parseToken(token: String) {
        if (token[0].isDigit()) {
            outputQueue.add(Number(token.toLong()))
        } else if (token == "*" || token == "+") {
            val operator = parseOperator(token[0])
            while (!operatorStack.isEmpty()
                && operatorStack.peek().precedence >= operator.precedence
                && operatorStack.peek() != LeftParenthesis
            ) {
                outputQueue.add(operatorStack.pop())
            }

            operatorStack.push(operator)
        } else if (token == "(") {
            operatorStack.push(LeftParenthesis)
        } else if (token == ")") {
            while (operatorStack.peek() != LeftParenthesis) {
                outputQueue.add(operatorStack.pop())
            }
            if (operatorStack.peek() == LeftParenthesis) {
                operatorStack.pop()
            }
        }
    }

    private fun parseOperator(c: Char) = if (c == '+') Plus else Multiply
}

sealed class OutputToken
data class Number(val value: Long) : OutputToken() {
    override fun toString(): String {
        return value.toString()
    }
}

abstract class Operator(private val symbol: Char, val precedence: Int) : OutputToken() {
    override fun toString(): String {
        return symbol.toString()
    }
}

object Plus : Operator('+', 2)
object Multiply : Operator('*', 1)
object LeftParenthesis : Operator('(', Int.MAX_VALUE)