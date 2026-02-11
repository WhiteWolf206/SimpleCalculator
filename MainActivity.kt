package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// I let an ai pick the colors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF4CAF50),
                    surface = Color(0xFF1C1C1C),
                    background = Color(0xFF121212)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var display by remember { mutableStateOf("0") } // displays the current number
    var firstNumber by remember { mutableStateOf("") } // stores the first number
    var operation by remember { mutableStateOf("") } // stores the operation
    var newNumber by remember { mutableStateOf(true) } // checks if the current number is new

    // functions

    fun calculate(): String {
        val num1 = firstNumber.toDoubleOrNull() ?: return "0"
        val num2 = display.toDoubleOrNull() ?: return "0"

        return when (operation) {
            "+" -> (num1 + num2).toString().removeSuffix(".0")
            "-" -> (num1 - num2).toString().removeSuffix(".0")
            "×" -> (num1 * num2).toString().removeSuffix(".0")
            "÷" -> if (num2 != 0.0) (num1 / num2).toString().removeSuffix(".0") else "Error"
            else -> display
        }
    }

    fun numberclick(number: String) {
        if (newNumber) {
            display = number
            newNumber = false
        } else {
            if (display == "0" && number != ".") {
                display = number
            } else if (!(number == "." && display.contains("."))) {
                display += number
            }
        }
    }

    fun operationclick(op: String) {
        if (operation.isNotEmpty() && !newNumber) {
            display = calculate()
        }
        firstNumber = display
        operation = op
        newNumber = true
    }

    fun equalclick() {
        if (operation.isNotEmpty()) {
            display = calculate()
            operation = ""
            firstNumber = ""
            newNumber = true
        }
    }

    fun clearclick() {
        display = "0"
        firstNumber = ""
        operation = ""
        newNumber = true
    }
}