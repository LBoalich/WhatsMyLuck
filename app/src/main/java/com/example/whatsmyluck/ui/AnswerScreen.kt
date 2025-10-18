package com.example.whatsmyluck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.whatsmyluck.R


@Composable
fun AnswerScreen(
    luckUiState: LuckUiState,
    onYesPressed: () -> Unit,
    onNoPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val luckArray = stringArrayResource(R.array.luck_array)

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Text (
            text = luckUiState.luckQuote,
            style = MaterialTheme.typography.titleLarge,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        Spacer( modifier = Modifier.height(40.dp))
        Text (
            text = luckUiState.currentAnswer,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
        )
        Spacer( modifier = Modifier.height(40.dp))
        Text (
            text = stringResource(R.string.seek),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
        )
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { onYesPressed() },
            ) {
                Text (
                    text = "Yes"
                )
            }
            Button(
                onClick = { onNoPressed() }
            ) {
                Text(
                    text = "No"
                )
            }
        }
    }
}