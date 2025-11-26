package com.example.blotihashiv.ui.theme.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StartScreenUiState(
    val isBoyDouble: Boolean = true,
    val winScore: Int = 301
)

class StartViewModel: ViewModel() {

    private val _uiState=MutableStateFlow(StartScreenUiState())

    val uiState:StateFlow<StartScreenUiState> = _uiState.asStateFlow()

    fun toggleBoyDouble(){
        _uiState.update { currentState ->
            currentState.copy(isBoyDouble = !currentState.isBoyDouble)
        }
    }
    fun setWinScore(score:Int){
        _uiState.update { currentState ->
            currentState.copy(winScore = score)
        }
    }
}