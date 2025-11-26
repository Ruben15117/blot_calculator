package com.example.blotihashiv.ui.theme.navigation

sealed class Screen(val route:String) {
    object start: Screen("startScreen")
    object calculator: Screen("ScoreScreen/{boyDouble}/{winScore}"){
        fun passArgs(boyDouble: Boolean, winScore: Int)="ScoreScreen/$boyDouble/$winScore"
    }
    

}