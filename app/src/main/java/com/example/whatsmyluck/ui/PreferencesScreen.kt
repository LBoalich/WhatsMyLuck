package com.example.whatsmyluck.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.whatsmyluck.R

@Composable
fun PreferencesScreen(
    onDismissRequest: () -> Unit,
    luckUiState: LuckUiState,
    onReset: () -> Unit,
    onToggleTrack: () -> Unit,
    updateNumQuestionsTracking: (String) -> Unit
    ) {
    val activity = (LocalContext.current as Activity)
    val showResetWarningDialog = remember { mutableStateOf(false) }
    val showTrackWarningDialog = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.reset),
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Start,
                )
                Switch(
                    checked = false,
                    onCheckedChange = { showResetWarningDialog.value = true }
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Track Questions",
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(8.dp),
                    textAlign = TextAlign.Start,
                )
                Switch(
                    checked = luckUiState.isTrackingQuestions,
                    onCheckedChange = { showTrackWarningDialog.value = true }
                )
            }
            if (luckUiState.isTrackingQuestions) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val focusManager = LocalFocusManager.current

                    Text(
                        text = stringResource(R.string.questions_total),
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                    OutlinedTextField(
                        value = luckUiState.displayNumQuestionsToAsk,
                        onValueChange = updateNumQuestionsTracking,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .padding(bottom = 32.dp, top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.close))
                }
            }
        }
    }

    when {
        showResetWarningDialog.value -> {
            WarningDialog(
                onCancelRequest = { showResetWarningDialog.value = false },
                {
                    showResetWarningDialog.value = false
                    onReset()
                },
                modifier = Modifier
            )
        }
    }

    when {
        showTrackWarningDialog.value -> {
            WarningDialog(
                onCancelRequest = { showTrackWarningDialog.value = false },
                onConfirmRequest = {
                    showTrackWarningDialog.value = false
                    onToggleTrack()
                },
                modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningDialog(
    onCancelRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    modifier: Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onCancelRequest,
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text("Warning!", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height((8.dp)))
                Text("This cannot be undone.")
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                ) {
                    Button(
                        onClick = onConfirmRequest,
                        modifier = Modifier
                            .padding(8.dp)
                    ) { Text("Confirm")}
                    Button(
                        onClick = onCancelRequest,
                        modifier = Modifier
                            .padding(8.dp)
                    ) { Text("Cancel")}
                }
            }
        })
}

