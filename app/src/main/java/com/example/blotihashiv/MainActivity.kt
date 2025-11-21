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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.blotihashiv.ui.theme.BlotiHashivTheme
import com.example.blotihashiv.ui.theme.activeBackground
import com.example.blotihashiv.ui.theme.activeText
import com.example.blotihashiv.ui.theme.inactiveBackground
import com.example.blotihashiv.ui.theme.inactiveText
import kotlin.Boolean
import kotlin.Int
import kotlin.Unit


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

@Suppress("SpellCheckingInspection")
@Composable
fun ScoreScreen() {
    var isTeam1Leading by remember { mutableStateOf(true) }
    var team1RoundScore by remember { mutableIntStateOf(0) }
    var team2RoundScore by remember { mutableIntStateOf(0) }
    var isRoundStarted by remember { mutableStateOf(false) }
    var target by remember { mutableIntStateOf(8) }
    var isKaput by remember { mutableStateOf(false) }
    var isQuansh by remember { mutableStateOf(false) }
    var isSur by remember { mutableStateOf(false) }
    val rounds = remember { mutableStateListOf<Round>() }
    rounds.add(Round(0, 0, 0, ""))
    val team1Total = rounds.sumOf { it.team1 }
    val team2Total = rounds.sumOf { it.team2 }
    val winningColor = Color.Green
    val defaultScoreColor = MaterialTheme.colorScheme.onSurface
    var team1Outside by remember { mutableIntStateOf(0) }
    var team2Outside by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        TargetRowList(
            targetValue = target,
            isInactive = isRoundStarted,

            ) { target = it }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DeleteButton(isRoundStarted, rounds)

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                TargetModifierButton(
                    isInactive = isRoundStarted,
                    isToggled = isKaput,
                    buttonText = stringResource(R.string.K)
                ) {
                    isKaput = !isKaput
                }
                TargetModifierButton(
                    isInactive = isRoundStarted,
                    isToggled = isQuansh,
                    buttonText = stringResource(R.string.Q)
                ) {
                    isQuansh = !isQuansh
                    if (!isQuansh and isSur) {
                        isSur = false
                    }
                }
                TargetModifierButton(
                    isInactive = isRoundStarted,
                    isToggled = isSur,
                    buttonText = stringResource(R.string.S)
                ) {
                    if (isQuansh) {
                        isSur = !isSur
                    }
                }
            }
            Button(
                onClick = {
                    isRoundStarted = !isRoundStarted
                },
            ) {
                Text(
                    if (isRoundStarted) {
                        stringResource(R.string.end)
                    } else {
                        stringResource(R.string.start)
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.team_1),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

            Text(
                stringResource(R.string.x),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                stringResource(R.string.team_2),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )
        }
        if (isRoundStarted) {
            Row {
                Box(
                    modifier=Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopStart
                ){
                    TargetColumnList(
                        team1Outside
                    ) {
                        team1Outside = it

                    }
                }
                Box(
                    modifier=Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ){
                    TargetColumnList(
                        outsideValue = if (isTeam1Leading) {
                            team1RoundScore
                        } else
                            team2RoundScore,
                        valueStart = 8,
                        valueEnd = 39,
                    ) {
                        if (isTeam1Leading) {
                            team1RoundScore = it
                        } else
                            team2RoundScore = it

                    }
                }

                Box(
                    modifier=Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    TargetColumnList(
                        team2Outside
                    ) {
                        team2Outside = it

                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(rounds) { index, round ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {



                        ScoreText(
                            text = "${round.team1}",
                            modifier = Modifier.weight(3f)
                        )
                        ScoreText(
                            text = "${round.target}",
                            modifier = Modifier.weight(1f)
                        )
                        ScoreText(
                            text = "${round.team2}",
                            modifier = Modifier.weight(3f)
                        )

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        Column {

            Row {
                Text(
                    "$team1Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    color = if (team1Total > 301 && team2Total < team1Total) winningColor else defaultScoreColor
                )
                Text(
                    "$team2Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    color = if (team2Total > 301 && team2Total > team1Total) winningColor else defaultScoreColor
                )
                Text(
                    "Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                horizontalArrangement = Arrangement.Center
            ) {

            }

            Spacer(modifier = Modifier.height(8.dp))

        }


    }
}

@Composable
fun TargetModifierButton(
    isToggled: Boolean,
    buttonText: String = stringResource(R.string.n_a),
    fontSize: TextUnit = 12.sp,
    buttonSize: Dp = 36.dp,
    isInactive: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .size(buttonSize),
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isToggled) activeBackground else inactiveBackground,
            contentColor = if (isToggled) activeText else inactiveText
        ),
        contentPadding = PaddingValues(0.dp),
        enabled = !isInactive,
    ) {
        Text(
            buttonText,
            fontSize = fontSize,
        )
    }
}

@Composable
fun TargetRowList(
    targetValue: Int,
    isInactive: Boolean,
    onNumberClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(42) { number ->
            val isSelected = (number + 8) == targetValue
            TargetModifierButton(
                isInactive = isInactive,
                isToggled = isSelected,
                buttonText = (number + 8).toString(),
                fontSize = 10.sp,
                buttonSize = 32.dp
            ) {
                onNumberClick(number + 8)
            }
        }
    }
}

@Composable
fun TargetColumnList(
    outsideValue: Int,
    valueStart: Int = 2,
    valueEnd: Int = 39,
    onNumberClick: (Int) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(valueEnd) { number ->
            val isSelected = (number + valueStart) == outsideValue
            TargetModifierButton(
                isToggled = isSelected,
                buttonText = (number + valueStart).toString(),
                fontSize = 10.sp,
                buttonSize = 32.dp
            ) {
                onNumberClick(number + valueStart)
            }
        }
    }

}

@Composable
fun ScoreText(
    text: String,
    textColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        textAlign = TextAlign.Center,
        color = textColor,
        modifier = modifier//if (team2Total > 301 && team2Total > team1Total) winningColor else defaultScoreColor
    )
}

@Composable
fun DeleteButton(
    isInactive: Boolean,
    rounds: SnapshotStateList<Round>
) {
    var showDialog by remember { mutableStateOf(false) }
    IconButton(
        enabled = !isInactive,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color(0xFFBE1818)
        ),
        onClick = { showDialog = !showDialog }
    ) {
        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.clear_all)) },
            confirmButton = {
                Button(
                    onClick = {
                        rounds.clear()
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}
//onClick = {
//    val t1 = team1Input.toIntOrNull() ?: 0
//    val t2 = team2Input.toIntOrNull() ?: 0
//    var tar = target
//    if (isKaput && tar < 25) {
//        tar = 25
//    }
//    val targetString = buildString {
//        if (isKaput) append("K")
//        append("$target ")
//        if (isQuansh) append("Q")
//        if (isSur) append("S")
//    }
//    rounds.add(Round(t1, t2, tar, targetString))
//    isSur = false
//    isQuansh = false
//    isKaput = false
//    team1Input = ""
//    team2Input = ""
//    target = 0
//},

@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview() {
    BlotiHashivTheme {
        ScoreScreen()
    }
}
