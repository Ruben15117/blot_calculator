package com.example.blotihashiv

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.blotihashiv.ui.theme.BlotiHashivTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlotiHashivTheme {
                ScoreScreen()
            }
        }
    }
}

class Round(var team1:Int,
            var team2:Int,
            var target: Int,
            var isKaput: Boolean,
            var isQuansh: Boolean,
            var isSur: Boolean)

@Composable
fun ScoreScreen() {
    var team1Input by remember { mutableStateOf("") }
    var team2Input by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var isKaput by remember { mutableStateOf(false) }
    var isQuansh by remember { mutableStateOf(false) }
    var isSur by remember { mutableStateOf(false) }
    val rounds = remember { mutableStateListOf<Round>() }
    val team1Total = rounds.sumOf { it.team1 }
    val team2Total = rounds.sumOf { it.team2 }git
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp,30.dp,16.dp,16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Blot Score Calculator", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = team1Input,
            onValueChange = { team1Input = it },
            label = { Text("Team 1 Points") },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType=KeyboardType.Number
                )
        )

        OutlinedTextField(
            value = team2Input,
            onValueChange = { team2Input = it },
            label = { Text("Team 2 Points") },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType=KeyboardType.Number
                )
        )

        OutlinedTextField(
            value = target,
            onValueChange = {target = it},
            label = { Text("Target")},
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType=KeyboardType.Number
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val activeBackground = Color(0xFF4CAF50)
            val inactiveBackground = Color(0xFFCCCCCC)
            val activeText = Color.White
            val inactiveText = Color.Black
            Button(
                modifier = Modifier
                    .padding(5.dp,2.dp),
                onClick = {
                    isKaput=!isKaput
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (isKaput) activeBackground else inactiveBackground,
                    contentColor = if (isKaput) activeText else inactiveText
                )
            ){
                Text("K")
            }
            Button(
                modifier = Modifier
                    .padding(5.dp,2.dp),
                onClick = {
                    isQuansh = !isQuansh
                },
                colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isQuansh) activeBackground else inactiveBackground,
                        contentColor = if (isQuansh) activeText else inactiveText
                )
            ){
                Text("Q")
            }
            Button(
                modifier = Modifier
                    .padding(5.dp,2.dp),
                onClick = {
                    isSur = !isSur
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (isSur) activeBackground else inactiveBackground,
                    contentColor = if (isSur) activeText else inactiveText
                )
            ){
                Text("S")
            }
        }
        Button(
            onClick = {
                val t1 = team1Input.toIntOrNull() ?: 0
                val t2 = team2Input.toIntOrNull() ?: 0
                val tar = target.toIntOrNull() ?: 8
                rounds.add(Round(t1, t2, tar, isKaput, isQuansh, isSur))
                isSur=false
                isQuansh=false
                isKaput=false
                team1Input = ""
                team2Input = ""
                target = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Round")
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Team 1", style = MaterialTheme.typography.bodyLarge)
            Text("Team 2", style = MaterialTheme.typography.bodyLarge)
            Text("Target", style = MaterialTheme.typography.bodyLarge)
        }
        LazyColumn {
            itemsIndexed(rounds) { index, round ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val tar = buildString {
                        if (round.isKaput) append("K")
                        append("${round.target} ")
                        if (round.isQuansh) append("Q")
                        if (round.isSur) append("S")
                    }
                    Text("${round.team1}")
                    Text("${round.team2}")
                    Text("$tar")
//                    TextButton(onClick = { rounds.removeAt(index) }) {
//                        Text("Delete", color = MaterialTheme.colorScheme.error)
//                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview(){
    BlotiHashivTheme {
        ScoreScreen()
    }
}
