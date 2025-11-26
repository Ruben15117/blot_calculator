package com.example.blotihashiv.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.blotihashiv.ui.theme.screens.ScoreScreen
import com.example.blotihashiv.ui.theme.screens.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.start.route) {
        composable(Screen.start.route) {
            StartScreen(navController = navController)
        }
        composable(
            route = Screen.calculator.route,
            arguments = listOf(
                navArgument("boyDouble") { type = NavType.BoolType },
                navArgument("winScore") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val boyDouble =backStackEntry.arguments?.getBoolean("boyDouble")?: true
            val winScore = backStackEntry.arguments?.getInt("winScore")?: 301
                ScoreScreen(navController = navController, boyDouble = boyDouble, winScore = winScore)
        }
    }
}
