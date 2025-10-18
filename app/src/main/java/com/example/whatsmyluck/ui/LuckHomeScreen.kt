package com.example.whatsmyluck.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LuckHomeScreen(
    luckUiState: LuckUiState,
    onRevealPressed: () -> Unit,
    onYesPressed: () -> Unit,
    onNoPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (luckUiState.isShowingHomepage) {
        QuestionScreen(
            luckUiState = luckUiState,
            onRevealPressed,
            modifier = modifier
        )
    } else {
        AnswerScreen(
            luckUiState,
            onYesPressed,
            onNoPressed,
            modifier = modifier
        )
    }
}