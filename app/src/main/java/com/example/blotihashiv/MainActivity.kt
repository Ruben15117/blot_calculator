package com.example.blotihashiv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.blotihashiv.ui.theme.BlotiHashivTheme
import com.example.blotihashiv.ui.theme.navigation.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlotiHashivTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
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

//@Preview(showBackground = true)
//@Composable
//fun ScoreScreenPreview() {
//    BlotiHashivTheme {
//        ScoreScreen()
//    }
//}

