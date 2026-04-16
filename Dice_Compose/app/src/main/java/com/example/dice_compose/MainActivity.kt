package com.example.dice_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    TwoDiceandImages(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun TwoDiceandImages(modifier: Modifier = Modifier) {
    var dice1Result by remember {mutableStateOf(0)}
    var dice2Result by remember {mutableStateOf(0)}

    val context = LocalContext.current

    val imageResource1 = when (dice1Result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    val imageResource2 = when (dice2Result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResource1),
                contentDescription = dice1Result.toString(),
                modifier = Modifier.size(180.dp)
            )
            Image(
                painter = painterResource(id = imageResource2),
                contentDescription = dice2Result.toString(),
                modifier = Modifier.size(180.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            dice1Result = (1..6).random()
            dice2Result = (1..6).random()

            if (dice1Result == dice2Result) {
                Toast.makeText(context, "Selamat, anda dapat dadu double!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Roll")
        }
    }
}