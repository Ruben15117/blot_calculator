package com.example.blotihashiv.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.blotihashiv.R
import com.example.blotihashiv.Round
import com.example.blotihashiv.ui.theme.BlotiHashivTheme
import com.example.blotihashiv.ui.theme.components.DeleteButton
import com.example.blotihashiv.ui.theme.components.ScoreText
import com.example.blotihashiv.ui.theme.components.TargetColumnList
import com.example.blotihashiv.ui.theme.components.TargetModifierButton
import com.example.blotihashiv.ui.theme.components.TargetRowList


@Composable
fun ScoreScreen(
    navController: NavController,
    boyDouble: Boolean,
    winScore: Int,
    scoreViewModel: ScoreViewModel=viewModel()
) {
    val uiState by scoreViewModel.uiState.collectAsState()
    val rounds =scoreViewModel.rounds
    LaunchedEffect(key1=Unit) {
        scoreViewModel.setupGame(winScore, boyDouble)
        rounds.add(Round(0,0,0,""))


    }
    val team1Total = scoreViewModel.team1Total
    val team2Total = scoreViewModel.team2Total
    val winningColor = Color.Green
    val defaultScoreColor = MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        //target selector
        TargetRowList(
            targetValue = uiState.target,
            isInactive = uiState.isRoundStarted,
            onNumberClick = scoreViewModel::onTargetChange
            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            DeleteButton(uiState.isRoundStarted, rounds)

            //kaput quansh sur buttons
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                TargetModifierButton(
                    isInactive = uiState.isRoundStarted,
                    isToggled = uiState.isKaput,
                    buttonText = stringResource(R.string.K),
                    onClick = scoreViewModel::toggleKaput
                )
                TargetModifierButton(
                    isInactive = uiState.isRoundStarted,
                    isToggled = uiState.isQuansh,
                    buttonText = stringResource(R.string.Q),
                    onClick = scoreViewModel::toggleQuansh
                )
                TargetModifierButton(
                    isInactive = uiState.isRoundStarted,
                    isToggled = uiState.isSur,
                    buttonText = stringResource(R.string.S),
                    onClick = scoreViewModel::toggleSur
                )
            }
            //round start
            Button(
                onClick = scoreViewModel::toggleRoundState
            ) {
                Text(
                    if (uiState.isRoundStarted) {
                        stringResource(R.string.end)
                    } else {
                        stringResource(R.string.start)
                    }
                )
            }
        }
        // team labels
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
        //round calculator
        if (uiState.isRoundStarted) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    TargetColumnList(
                        uiState.team1Outside,
                        onNumberClick = scoreViewModel::onTeam1OutsideChange
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TargetColumnList(
                        outsideValue = if (uiState.isTeam1Leading) {
                            uiState.team1RoundScore
                        } else
                            uiState.team2RoundScore,
                        valueStart = 8,
                        valueEnd = 39,
                    ) {
                        if (uiState.isTeam1Leading) {
                            scoreViewModel.onTeam1RoundScoreChange(it)
                        } else
                            scoreViewModel.onTeam2RoundScoreChange(it)

                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    TargetColumnList(
                        uiState.team2Outside,
                        onNumberClick = scoreViewModel::onTeam2OutsideChange
                    )
                }
            }
            //rounds history
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

        //total score block
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

@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview() {
    val navController = rememberNavController()
    BlotiHashivTheme {
        ScoreScreen(navController, true, 301)
    }
}
