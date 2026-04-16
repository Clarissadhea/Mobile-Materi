package com.example.calculator_xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var etInputAmount: TextInputEditText
    private lateinit var autoCompleteTipPercentage: AutoCompleteTextView
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var tvTipResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInputAmount = findViewById(R.id.etInputAmount)
        autoCompleteTipPercentage = findViewById(R.id.autoCompleteTipPercentage)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        tvTipResult = findViewById(R.id.tvTipResult)

        val percentages = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, percentages)
        autoCompleteTipPercentage.setAdapter(adapter)

        etInputAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { calculateTip() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        autoCompleteTipPercentage.setOnItemClickListener { _, _, _, _ ->
            calculateTip()
        }

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
        calculateTip()
    }

    private fun calculateTip() {
        val amountStr = etInputAmount.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        val percentageStr = autoCompleteTipPercentage.text.toString().replace("%", "")
        val percentage = percentageStr.toDoubleOrNull() ?: 15.0

        var tip = amount * (percentage / 100.0)

        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        tvTipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}