package com.example.whatsmyluck.ui

import android.R.attr.fontWeight
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.whatsmyluck.R


@Composable
fun HelpScreen(onDismissRequest: () -> Unit) {
    val activity = (LocalContext.current as Activity)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(R.string.help_title),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.about),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.about_description),
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Left,
            )
            Text(
                text = stringResource(R.string.preferences_guide),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.reset),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.reset_description),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Left,
            )
            Text(
                text = stringResource(R.string.questions_total),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.questions_total_description),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(8.dp),
                textAlign = TextAlign.Left,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                ) {
                    Text( text = stringResource(R.string.close) )
                }
            }
        }
    }
}
