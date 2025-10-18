package com.example.whatsmyluck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whatsmyluck.R


@Composable
fun LuckApp(
    modifier: Modifier = Modifier,
    luckViewModel: LuckViewModel = viewModel(
        factory = LuckViewModel.Factory
    )
) {
    val luckUiState by luckViewModel.uiState.collectAsState()
    val answersList = stringArrayResource(R.array.answers_array).toList()
    val luckList = stringArrayResource(R.array.luck_array).toList()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                luckUiState,
                onReset = { luckViewModel.resetLuck() },
                onToggleTrack = { luckViewModel.toggleTracking() },
                updateNumberQuestionsTracking = { newNum -> luckViewModel.updateNumQuestionsToTrack(newNum) },
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LuckHomeScreen(
                luckUiState = luckUiState,
                onRevealPressed = { luckViewModel.newAnswer(answersList.random()) },
                onYesPressed = { luckViewModel.answerYes(luckList) },
                onNoPressed = { luckViewModel.answerNo(luckList) },
                modifier = modifier
            )
        }
    }
}