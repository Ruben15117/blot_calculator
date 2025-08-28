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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.blotihashiv.ui.theme.BlotiHashivTheme
import com.example.blotihashiv.ui.theme.activeBackground
import com.example.blotihashiv.ui.theme.activeText
import com.example.blotihashiv.ui.theme.inactiveBackground
import com.example.blotihashiv.ui.theme.inactiveText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlotiHashivTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScoreScreen()
                }
            }
        }
    }
}
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
    val team2Total = rounds.sumOf { it.team2 }
    val winningColor = Color.Green
    val defaultScoreColor = MaterialTheme.colorScheme.onSurface
    val team2FocusRequester = remember { FocusRequester() }
    val targetFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Text("Blot Score Calculator",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Center)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp, 9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Team 1",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center)

            Text("Team 2",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center)

            Text("Target",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.weight(1f))
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(rounds) { index, round ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text("${round.team1}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    Text("${round.team2}",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        round.targetString,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = { rounds.removeAt(index) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier.size(30.dp)
                        )
                   }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Row{
                Text("$team1Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    color=if(team1Total>301 && team2Total<team1Total) winningColor else defaultScoreColor)
                Text("$team2Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    color=if(team2Total>301 && team2Total>team1Total) winningColor else defaultScoreColor)
                Text("Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OutlinedTextField(
                    value = team1Input,
                    onValueChange = { team1Input = it },
                    label = { Text("Team 1") },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType=KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    keyboardActions = KeyboardActions(
                        onNext = { team2FocusRequester.requestFocus() }
                    ),
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = team2Input,
                    onValueChange = { team2Input = it },
                    label = { Text("Team 2") },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType=KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    keyboardActions = KeyboardActions(
                        onNext = { targetFocusRequester.requestFocus() }
                    ),
                    modifier = Modifier.weight(1f)
                        .focusRequester(team2FocusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = target,
                    onValueChange = {target = it},
                    label = { Text("Target")},
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType=KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier.weight(1f)
                        .focusRequester(targetFocusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.Center
            ) {
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
                        if(!isQuansh and isSur){
                            isSur = false
                        }
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
                        if(isQuansh){
                            isSur = !isSur
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isSur) activeBackground else inactiveBackground,
                        contentColor = if (isSur) activeText else inactiveText
                    )
                ){
                    Text("S")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val t1 = team1Input.toIntOrNull() ?: 0
                    val t2 = team2Input.toIntOrNull() ?: 0
                    var tar = target.toIntOrNull() ?: 8
                    if(isKaput && tar<25){
                        tar=25
                    }
                    val targetString = buildString {
                        if (isKaput) append("K")
                        append("$target ")
                        if (isQuansh) append("Q")
                        if (isSur) append("S")
                    }
                    rounds.add(Round(t1, t2, tar, targetString))
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
