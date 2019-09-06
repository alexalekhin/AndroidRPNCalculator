package ru.alexalekhin.pushapptest

import kotlin.math.round

class RPNCalculator {

    fun calculate(input: String): String {
        val rpn = toRPN(input)
        val elementStack = mutableListOf<Double>()
        val tokens = rpn.split(" ").filter { it != "" }
        for (t in tokens) {
            val d = t.toDoubleOrNull()
            when {
                d != null -> elementStack.add(d)
                elementStack.size < 2 -> throw IllegalArgumentException("Stack contains too few operands")
                (t.length > 1) || (t !in operations) -> throw IllegalArgumentException("$t is not a valid token")
                else -> {
                    val d1 = elementStack.removeAt(elementStack.lastIndex)
                    val d2 = elementStack.removeAt(elementStack.lastIndex)
                    elementStack.add(
                        when (t) {
                            "+" -> (d2 + d1).round(NUM_OF_DIGITS_AFTER_DOT)
                            "-" -> (d2 - d1).round(NUM_OF_DIGITS_AFTER_DOT)
                            "×" -> (d2 * d1).round(NUM_OF_DIGITS_AFTER_DOT)
                            "÷" -> (d2 / d1).round(NUM_OF_DIGITS_AFTER_DOT)
                            "%" -> ((d2 / d1) * WHOLE_VALUE_PERCENT).round(NUM_OF_DIGITS_AFTER_DOT)
                            else -> TODO("Add new operations")
                        }
                    )
                }
            }
        }
        return elementStack.joinToString("")
    }

    private fun toRPN(input: String): String {
        val sb = StringBuilder("")
        val stack = mutableListOf<String>()
        val tokens = input.split(" ").filter { it != "" }
        for (t in tokens) {
            val d = t.toDoubleOrNull()
            when {
                d != null -> sb.append(t).append(' ')
                else -> {
                    for(idx in stack.lastIndex downTo 0) {
                        val op = stack[idx]
                        if(operations.indexOf(op) >= operations.indexOf(t)) {
                            sb.append(stack.removeAt(stack.lastIndex)).append(' ')
                        } else {
                            break
                        }
                    }
                    stack.add(t)
                }
            }
        }
        while (stack.isNotEmpty()) sb.append(stack.removeAt(stack.lastIndex)).append(' ')
        return sb.toString()
    }

    companion object {
        //Отсортированы по возрастанию приоритета
        val operations = arrayListOf("+", "-", "×", "÷", "%")
        const val WHOLE_VALUE_PERCENT = 100.0f
        const val NUM_OF_DIGITS_AFTER_DOT = 10
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0f
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}
