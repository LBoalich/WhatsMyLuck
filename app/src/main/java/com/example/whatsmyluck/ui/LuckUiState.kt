package com.example.whatsmyluck.ui

data class LuckUiState(
    val totalQuestions: Double = 0.0,
    val totalYes: Double = 0.0,
    val currentAnswer: String = "",
    val currentLuck: Double = 0.0,
    val luckQuote: String = "",
    val isShowingHomepage: Boolean = true,
    val isTrackingQuestions: Boolean = false,
    val numQuestionsToAsk: Double = Double.POSITIVE_INFINITY,
    val displayNumQuestionsToAsk: String = ""
)
