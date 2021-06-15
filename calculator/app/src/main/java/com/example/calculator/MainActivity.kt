package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.txt_expression)
    }
    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.txt_result)
    }

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.btn_0 -> numberButtonClicked("0")
            R.id.btn_1 -> numberButtonClicked("1")
            R.id.btn_2 -> numberButtonClicked("2")
            R.id.btn_3 -> numberButtonClicked("3")
            R.id.btn_4 -> numberButtonClicked("4")
            R.id.btn_5 -> numberButtonClicked("5")
            R.id.btn_6 -> numberButtonClicked("6")
            R.id.btn_7 -> numberButtonClicked("7")
            R.id.btn_8 -> numberButtonClicked("8")
            R.id.btn_9 -> numberButtonClicked("9")

            R.id.btn_plus -> operatorButtonClicked("+")
            R.id.btn_minus -> operatorButtonClicked("-")
            R.id.btn_multi -> operatorButtonClicked("X")
            R.id.btn_div -> operatorButtonClicked("/")
            R.id.btn_mod -> operatorButtonClicked("%")
        }
    }

    private fun numberButtonClicked(number: String) {
        if (isOperator) {
            expressionTextView.append(" ")
        }
        isOperator = false

        val expressionText = expressionTextView.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        expressionTextView.append(number)
        resultTextView.text = calculateExpression()
    }

    private fun operatorButtonClicked(operator: String) {
        if (expressionTextView.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTextView.append(" $operator")
            }

        }
        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length - 1, expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        expressionTextView.text = ssb
        isOperator = true
        hasOperator = true
    }

    fun resultButtonClicked(v: View) {
        val expressionTexts = expressionTextView.text.split(" ")
        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }
        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "수식을 완성해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()

            return
        }
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false

    }


    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "X" -> (exp1 * exp2).toString()
            "%" -> (exp1 % exp2).toString()
            "/" -> (exp1 / exp2).toString()
            else -> ""
        }
    }


    fun clearButtonClicked(v: View) {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyButtonClicked(v: View) {

    }


    fun closeHistoryButtonClicked(v: View) {

    }

    fun historyClearButtonClicked(v: View) {
        // TODO 디비에서 모든 기록 삭제
        // TODO 뷰에서 모든 기록 삭제
    }
}
fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

