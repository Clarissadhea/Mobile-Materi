package com.example.calculator_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator_compose.ui.theme.Calculator_ComposeTheme
import java.text.NumberFormat
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator_ComposeTheme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 32.dp, vertical = 40.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun TipCalculatorApp(modifier: Modifier = Modifier) {
    var inputAmount by rememberSaveable {mutableStateOf("")}
    var expanded by rememberSaveable {mutableStateOf(false)}
    var selectedPercentage by rememberSaveable {mutableStateOf(15)}
    var roundUp by rememberSaveable {mutableStateOf(false)}

    val amount = inputAmount.toDoubleOrNull() ?: 0.0
    var tip = amount * (selectedPercentage / 100.0)

    if (roundUp) {
        tip = ceil(tip)
    }

    val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
    val percentages = listOf(15, 18, 20)

    Column(
        modifier = modifier.fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        TextField(
            value = inputAmount,
            onValueChange = {inputAmount = it},
            label = { Text(stringResource(id = R.string.bill_amount))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = "Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            OutlinedTextField(
                value = "$selectedPercentage%",
                onValueChange = {},
                readOnly = true,
                label = {Text(stringResource(id = R.string.tip_percentage))},
                leadingIcon = {
                    Text(text = "%", fontSize = 18.sp)
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                percentages.forEach { percentage ->
                    DropdownMenuItem(
                        text = {Text("$percentage%")},
                        onClick = {
                            selectedPercentage = percentage
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(stringResource(id = R.string.round_up_tip))
            Switch(
                checked = roundUp,
                onCheckedChange = {roundUp = it}
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.tip_amount, formattedTip),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}