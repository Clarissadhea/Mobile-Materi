package com.example.dice_xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivDadu1 = findViewById<ImageView>(R.id.ivDadu1)
        val ivDadu2 = findViewById<ImageView>(R.id.ivDadu2)
        val btnRoll = findViewById<Button>(R.id.btnRoll)

        btnRoll.setOnClickListener {
            val dadu1Value = Random.nextInt(1, 7)
            val dadu2Value = Random.nextInt(1, 7)

            ivDadu1.setImageResource(getDiceImageResource(dadu1Value))
            ivDadu2.setImageResource(getDiceImageResource(dadu2Value))

            if (dadu1Value == dadu2Value) {
                Toast.makeText(this, "Selamat, anda dapat dadu double!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getDiceImageResource(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}