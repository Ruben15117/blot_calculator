package com.example.blotihashiv.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.blotihashiv.R
import com.example.blotihashiv.ui.theme.BlotiHashivTheme
import com.example.blotihashiv.ui.theme.activeBackground
import com.example.blotihashiv.ui.theme.activeText
import com.example.blotihashiv.ui.theme.inactiveBackground
import com.example.blotihashiv.ui.theme.inactiveText
import com.example.blotihashiv.ui.theme.navigation.Screen

@Composable()
fun StartScreen(
    navController: NavController,
    startViewModel: StartViewModel = viewModel()
    ) {
    val uiState by startViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Button(
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (uiState.isBoyDouble) activeBackground else inactiveBackground,
                contentColor = if (uiState.isBoyDouble) activeText else inactiveText
            ),
            onClick = {
                startViewModel.toggleBoyDouble()
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(stringResource(R.string.Boy_x2))
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Button(
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (uiState.winScore==151) activeBackground else inactiveBackground,
                    contentColor = if (uiState.winScore==151) activeText else inactiveText
                ),
                onClick = {
                    startViewModel.setWinScore(151)
                }) {
                Text("151")
            }
            Button(
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (uiState.winScore==301) activeBackground else inactiveBackground,
                    contentColor = if (uiState.winScore==301) activeText else inactiveText
                ),
                onClick = {
                    startViewModel.setWinScore(301)
                }) {
                Text("301")
            }
        }
        Button(onClick = { navController.navigate(
            Screen.calculator.passArgs(uiState.isBoyDouble, uiState.winScore)
        ) }) {
            Text(stringResource(R.string.start))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    val navController = rememberNavController()
    BlotiHashivTheme {
        StartScreen(navController)
    }
}