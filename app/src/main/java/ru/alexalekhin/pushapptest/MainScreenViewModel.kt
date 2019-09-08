package ru.alexalekhin.pushapptest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.floor
import kotlin.math.truncate

class MainScreenViewModel : ViewModel() {
    private val calculator = RPNCalculator()

    val result: MutableLiveData<String> = MutableLiveData("")
    val expression: MutableLiveData<String> = MutableLiveData("")
    private var inputHistory: MutableList<ExpressionElement> = mutableListOf()

    private var isCommaUsed: Boolean = false
    private var isCalculationPerformed = false

    fun addNumber(number: String) {
        expression.value += number
        inputHistory.add(ExpressionElement.NUMBER)
    }

    fun addComma() {
        if (!isCommaUsed)
            if (inputHistory.isNotEmpty()) {
                when (inputHistory[inputHistory.lastIndex]) {
                    ExpressionElement.SIGN -> {
                        expression.value += "0."
                        inputHistory.add(ExpressionElement.NUMBER)
                        inputHistory.add(ExpressionElement.COMMA)
                        isCommaUsed = true
                    }
                    ExpressionElement.NUMBER -> {
                        expression.value += "."
                        inputHistory.add(ExpressionElement.COMMA)
                        isCommaUsed = true
                    }
                    ExpressionElement.OPERATION -> {
                        expression.value += " 0."
                        inputHistory.add(ExpressionElement.NUMBER)
                        inputHistory.add(ExpressionElement.COMMA)
                        isCommaUsed = true
                    }
                    ExpressionElement.COMMA -> {
                        expression.value =
                            expression.value!!.substring(0, expression.value!!.lastIndex)
                        inputHistory.removeAt(inputHistory.lastIndex)
                    }
                }
            } else {
                expression.value += "0."
                inputHistory.add(ExpressionElement.NUMBER)
                inputHistory.add(ExpressionElement.COMMA)
                isCommaUsed = true
            }
        isCalculationPerformed = false
    }

    fun addSign() {
        if (inputHistory.isNotEmpty()) {
            when (inputHistory[inputHistory.lastIndex]) {
                ExpressionElement.SIGN -> {
                    expression.value =
                        expression.value!!.substring(0, expression.value!!.lastIndex)
                    inputHistory.removeAt(inputHistory.lastIndex)
                }
                ExpressionElement.NUMBER -> {
                    //ignore user input
                }
                ExpressionElement.OPERATION -> {
                    expression.value += "-"
                    inputHistory.add(ExpressionElement.SIGN)
                }
                ExpressionElement.COMMA -> {
                }
            }
        } else {
            expression.value += "-"
            inputHistory.add(ExpressionElement.SIGN)
        }
    }

    fun addOperation(operation: String) {
        if (inputHistory.isNotEmpty()) {
            when (inputHistory[inputHistory.lastIndex]) {
                ExpressionElement.OPERATION -> {
                    expression.value =
                        expression.value!!.substring(
                            0,
                            expression.value!!.length - OPERATION_REMOVAL_SYMBOLS_NUM
                        ) + " $operation "
                }
                ExpressionElement.NUMBER -> {
                    expression.value += " $operation "
                    inputHistory.add(ExpressionElement.OPERATION)
                }
                ExpressionElement.SIGN -> {
                    expression.value =
                        expression.value!!.substring(
                            0,
                            expression.value!!.length - OPERATION_REMOVAL_SYMBOLS_NUM
                        ) + " $operation "
                    inputHistory[inputHistory.lastIndex] =
                        ExpressionElement.OPERATION
                }
                ExpressionElement.COMMA -> {
                    expression.value += "0 $operation "
                }
            }
            isCommaUsed = false
        }
        isCalculationPerformed = false
    }

    fun clearAll() {
        expression.value = ""
        result.value = ""
        inputHistory.clear()
        isCommaUsed = false
        isCalculationPerformed = false
    }

    fun calculate() {
        if (inputHistory.isNotEmpty()) {
            if (!isCalculationPerformed) {
                performCalculation()
            } else {
                val tokens = expression.value!!.split(" ")
                if (tokens.size == 1) return
                val newExpression =
                    result.value + " ${tokens[tokens.lastIndex - 1]} ${tokens[tokens.lastIndex]}"
                inputHistory.clear()
                newExpression.forEach {
                    when (it) {
                        '-' -> inputHistory.add(ExpressionElement.SIGN)
                        ',' -> inputHistory.add(ExpressionElement.COMMA)
                        in operations -> inputHistory.add(ExpressionElement.OPERATION)
                        ' ' -> {
                        }
                        else -> inputHistory.add(ExpressionElement.NUMBER)
                    }
                }
                expression.value = newExpression
                performCalculation()
            }
        }
    }

    private fun performCalculation() {
        when (inputHistory[inputHistory.lastIndex]) {
            ExpressionElement.NUMBER -> {
                val calculationResult = calculator.calculate(expression.value!!)
                result.value =
                    if ((calculationResult.toDouble() - floor(calculationResult.toDouble())) == FLOAT_ZERO) {
                        truncate(calculationResult.toDouble()).toLong().toString()
                    } else {
                        calculationResult
                    }
                isCalculationPerformed = true
            }
            else -> {
            }
        }
    }

    companion object {
        const val FLOAT_ZERO = 0.0
        const val OPERATION_REMOVAL_SYMBOLS_NUM = 3
        val operations = arrayListOf('+', '-', '×', '÷', '%')
    }

    enum class ExpressionElement {
        NUMBER, OPERATION, SIGN, COMMA
    }
}