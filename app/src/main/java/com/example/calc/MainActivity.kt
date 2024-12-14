package com.example.calc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var txtInput: TextView
    private lateinit var txtResult: TextView

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtInput = findViewById(R.id.txtInput)
        txtResult = findViewById(R.id.txtRes)


        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { numberAction(it) }
        }


        val operationButtons = listOf(
            R.id.btnAdd, R.id.btnSub, R.id.btnMult, R.id.btnDiv, R.id.btnPercent
        )
        operationButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { operationAction(it) }
        }


        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAction() }
        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener { backspaceAction() }
        findViewById<Button>(R.id.btnEq).setOnClickListener { equalsAction() }
        findViewById<Button>(R.id.btnParenthesis).setOnClickListener { parenthesisAction() }
        findViewById<Button>(R.id.btnPosNeg).setOnClickListener { toggleSignAction() }
    }

    private fun numberAction(view: View) {
        if (view is Button) {
            val value = view.text.toString()
            if (value == ".") {
                if (canAddDecimal) {
                    txtInput.append(value)
                    canAddDecimal = false
                }
            } else {
                txtInput.append(value)
            }
            canAddOperation = true
            updatePreviewResult()
        }
    }

    private fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            txtInput.append(view.text.toString())
            canAddOperation = false
            canAddDecimal = true
            updatePreviewResult()
        }
    }

    private fun clearAction() {
        txtInput.text = ""
        txtResult.text = ""
        canAddOperation = false
        canAddDecimal = true
    }

    private fun backspaceAction() {
        val length = txtInput.text.length
        if (length > 0) {
            val lastChar = txtInput.text.last()
            txtInput.text = txtInput.text.substring(0, length - 1)
            if (lastChar == '.') canAddDecimal = true
        }
    }

    private fun equalsAction() {
        try {
            val expression = txtInput.text.toString()
                .replace("÷", "/")
                .replace("×", "*")
                .replace("%", "/100")

            val result = ExpressionBuilder(expression).build().evaluate()
            txtInput.text = result.toString()  // Replace input with result
            txtResult.text = ""
        } catch (e: Exception) {
            txtResult.text = "Error"
        }
    }

    private fun parenthesisAction() {
        val input = txtInput.text.toString()
        val openCount = input.count { it == '(' }
        val closeCount = input.count { it == ')' }

        if (openCount == closeCount || input.isEmpty() || input.last() in "+-*/(") {
            txtInput.append("(")
        } else if (openCount > closeCount && input.last() !in "+-*/(") {
            txtInput.append(")")
        }
    }

    private fun toggleSignAction() {
        val input = txtInput.text.toString()

        if (input.isEmpty()) return

        val lastNumberIndex = input.indexOfLast { it in "+-*/()" }
        val lastNumber = if (lastNumberIndex == -1) {
            input
        } else {
            input.substring(lastNumberIndex + 1)
        }

        val newNumber = if (lastNumber.startsWith("-")) {
            lastNumber.removePrefix("-")
        } else {
            "-$lastNumber"
        }

        txtInput.text = if (lastNumberIndex == -1) {
            newNumber
        } else {
            input.substring(0, lastNumberIndex + 1) + newNumber
        }
    }

    private fun updatePreviewResult() {
        val input = txtInput.text.toString()

        if (input.any { it in "+-×÷" }) {
            try {
                val expression = input
                    .replace("÷", "/")
                    .replace("×", "*")
                    .replace("%", "/100")

                val result = ExpressionBuilder(expression).build().evaluate()
                txtResult.text = result.toString()
            } catch (e: Exception) {
                txtResult.text = ""
            }
        } else {
            txtResult.text = ""
        }
    }

}