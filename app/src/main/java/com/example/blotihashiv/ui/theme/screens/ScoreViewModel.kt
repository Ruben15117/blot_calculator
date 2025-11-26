package com.example.blotihashiv.ui.theme.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.blotihashiv.Round
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ScoreUiState(
    val isTeam1Leading: Boolean = true,
    val team1RoundScore: Int = 0,
    val team2RoundScore: Int = 0,
    val team1Outside: Int = 0,
    val team2Outside: Int = 0,
    val isRoundStarted: Boolean = false,
    val target: Int = 8,
    val isKaput: Boolean = false,
    val isQuansh: Boolean = false,
    val isSur: Boolean = false
)

class ScoreViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScoreUiState())

    val uiState: StateFlow<ScoreUiState> = _uiState.asStateFlow()

    var winScore: Int = 301
        private set
    var boyDouble: Boolean = true
        private set

    val rounds = mutableStateListOf<Round>()
    val team1Total: Int
        get() = rounds.sumOf { it.team1 }
    val team2Total: Int
        get() = rounds.sumOf { it.team2 }

    fun setupGame(WinScore: Int, BoyDouble: Boolean) {
        winScore = WinScore
        boyDouble = BoyDouble
        if (rounds.isEmpty()) {
            rounds.add(Round(0, 0, 0, ""))
        }
    }

    fun toggleKaput() {
        _uiState.update { currentState ->
            currentState.copy(isKaput = !currentState.isKaput)
        }
    }

    fun toggleQuansh() {
        _uiState.update { currentState ->
            val newQuansh = !currentState.isQuansh
            val newSur = if (newQuansh) false else currentState.isSur
            currentState.copy(isQuansh = newQuansh, isSur = newSur)

        }
    }

    fun toggleSur() {
        _uiState.update { currentState ->
            currentState.copy(isSur = !currentState.isSur)
        }
    }

    fun onTargetChange(newTarget: Int) {
        _uiState.update {currentState->
            currentState.copy(target = newTarget) }
    }
    fun toggleRoundState() {
        val wasRoundStarted = _uiState.value.isRoundStarted
        _uiState.update { it.copy(isRoundStarted = !it.isRoundStarted) }
        //logic ++
    }

    private fun resetRound() {
        _uiState.update {
            it.copy(
                team1RoundScore = 0,
                team2RoundScore = 0,
                team1Outside = 0,
                team2Outside = 0,
                target = 8,
                isKaput = false,
                isQuansh = false,
                isSur = false
            )
        }
    }
    fun deleteLastRound() {
        if (rounds.size > 1) {
            rounds.removeAt(rounds.lastIndex)
        }
    }
    fun onTeam1RoundScoreChange(score: Int) {
        _uiState.update { it.copy(team1RoundScore = score) }
    }
    fun onTeam2RoundScoreChange(score: Int) {
        _uiState.update { it.copy(team2RoundScore = score) }
    }
    fun onTeam1OutsideChange(score: Int) {
        _uiState.update { it.copy(team1Outside = score) }
    }
    fun onTeam2OutsideChange(score: Int) {
        _uiState.update { it.copy(team2Outside = score) }
    }


}