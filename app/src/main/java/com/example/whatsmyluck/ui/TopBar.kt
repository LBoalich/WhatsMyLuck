package com.example.whatsmyluck.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.whatsmyluck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    luckUiState: LuckUiState,
    onReset: () -> Unit,
    onToggleTrack: () -> Unit,
    updateNumberQuestionsTracking: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val openHelpScreen = remember { mutableStateOf(false) }
    val openPreferencesScreen = remember { mutableStateOf(false) }

    when {
        openHelpScreen.value -> {
            HelpScreen (
                onDismissRequest = { openHelpScreen.value = false },
            )
        }
    }

    when {
        openPreferencesScreen.value -> {
            PreferencesScreen (
                onDismissRequest = { openPreferencesScreen.value = false },
                onReset = { onReset() },
                onToggleTrack = { onToggleTrack() },
                updateNumQuestionsTracking = updateNumberQuestionsTracking,
                luckUiState = luckUiState
            )
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { openPreferencesScreen.value = true }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Preferences"
                )
            }
        },
        actions = {
            IconButton(onClick = { openHelpScreen.value = true }) {
                Icon(
                    imageVector = Icons.Outlined.QuestionMark,
                    contentDescription = "Help"
                )
            }
        }
    )
}