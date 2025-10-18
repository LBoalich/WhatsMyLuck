package com.example.whatsmyluck.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.whatsmyluck.LuckApplication
import com.example.whatsmyluck.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.Double

class LuckViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LuckUiState())
    val uiState: StateFlow<LuckUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            userPreferencesRepository.isTrackingQuestions
                .combine(userPreferencesRepository.numQuestionsToAsk) {
                        isTrackingQuestions, numQuestionsToAsk ->
                    LuckUiState(
                        isTrackingQuestions = isTrackingQuestions,
                        numQuestionsToAsk = numQuestionsToAsk,
                        displayNumQuestionsToAsk = determineDisplayNumQuestions(numQuestionsToAsk)
                    )
                }
                .collect { luckUiState ->
                    _uiState.value = luckUiState
                }
        }
    }

    /*
    fun resetLuck() {
        _uiState.value = LuckUiState()
    }
     */

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LuckApplication)
                LuckViewModel(application.userPreferencesRepository)
            }
        }
    }


    fun resetLuck() {
        selectTracking(false)
        selectNumQuestions(Double.POSITIVE_INFINITY)
        _uiState.update { currentState ->
            currentState.copy(
                totalQuestions = 0.0,
                totalYes = 0.0,
                currentAnswer = "",
                currentLuck = 0.0,
                luckQuote= "",
                isShowingHomepage = true,
                isTrackingQuestions = false,
                numQuestionsToAsk = Double.POSITIVE_INFINITY,
                displayNumQuestionsToAsk = ""
            )
        }
    }


    private fun updateLuck(incYes: Double, newLuck: Double, newLuckQuote: String) {
        _uiState.update { currentState ->
            currentState.copy(
                totalQuestions = currentState.totalQuestions.inc(),
                totalYes = currentState.totalQuestions.plus(incYes),
                currentLuck = newLuck,
                luckQuote = newLuckQuote,
                isShowingHomepage = true
            )
        }
    }

    fun newAnswer(newAnswer: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentAnswer = newAnswer,
                isShowingHomepage = false
            )
        }
    }

    fun answerYes(luckQuotes: List<String>) {
        val newTotal = _uiState.value.totalQuestions + 1
        val newYes = _uiState.value.totalYes + 1
        val newLuck = newYes / newTotal
        val newLuckQuote = luckQuotes[currentLuckQuote(newLuck)]
        updateLuck(1.0, newLuck, newLuckQuote)
    }

    fun answerNo(luckQuotes: List<String>) {
        val newTotal = _uiState.value.totalQuestions + 1
        val newLuck = _uiState.value.totalYes / newTotal
        val newLuckQuote = luckQuotes[currentLuckQuote(newLuck)]
        updateLuck(0.0, newLuck, newLuckQuote)
    }

    private fun currentLuckQuote(newLuck : Double): Int {
        return when (newLuck) {
            in 0.1..0.24 -> 0
            in 0.241..0.49 -> 1
            in 0.491..0.74 -> 2
            in 0.741..1.0 -> 3
            else -> 4
        }
    }

    fun toggleTracking() {
        if (_uiState.value.isTrackingQuestions) {
            selectTracking(false)
            updateNumQuestionsToTrack("infinity")
            _uiState.update { currentState ->
                currentState.copy(
                    isTrackingQuestions = false
                )
            }
        } else {
            selectTracking(true)
            updateNumQuestionsToTrack("10")
            _uiState.update { currentState ->
                currentState.copy(
                    isTrackingQuestions = true
                )
            }
        }
    }

    fun updateNumQuestionsToTrack(newNum: String) {
        try {
            val newNumber = newNum.toDouble()
            selectNumQuestions(newNumber)
            _uiState.update { currentState ->
                currentState.copy(
                    numQuestionsToAsk = newNumber,
                    displayNumQuestionsToAsk = determineDisplayNumQuestions(newNumber)
                )
            }
        } catch (e: NumberFormatException) {
            val newNumber = Double.POSITIVE_INFINITY
            selectNumQuestions(newNumber)
            determineDisplayNumQuestions(newNumber)
            _uiState.update { currentState ->
                currentState.copy(
                    numQuestionsToAsk = newNumber,
                    displayNumQuestionsToAsk = determineDisplayNumQuestions(newNumber)
                )
            }
        }
    }

    fun determineDisplayNumQuestions(numQuestions: Double): String {
        if (numQuestions == Double.POSITIVE_INFINITY) {
            return ""
        } else {
            return numQuestions.toInt().toString()
        }
    }

    fun selectTracking(isTrackingQuestions: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveTrackingPreference(isTrackingQuestions)
        }
    }

    fun selectNumQuestions(numQuestionsToAsk: Double) {
        viewModelScope.launch {
            userPreferencesRepository.saveNumQuestionsPreference(numQuestionsToAsk)
        }
    }
}

