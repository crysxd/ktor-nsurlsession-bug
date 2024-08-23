package com.test.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.test.myapplication.theme.MyApplicationTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    var sent by remember { mutableStateOf(false) }
    var received by remember { mutableStateOf(false) }
    var completed by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Step("Send request", completed = sent)
                Step("Receive response", completed = received)
                Step("Complete request", completed = completed, failed = failed)
                Text(text = error, textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
            }
        }
    }

    LaunchedEffect(Unit) {
        KtorBugSample.reproduceBug(
            onSent = { sent = true },
            onReceived = { received = true },
            onCompleted = { e ->
                completed = true
                failed = e != null
                error = e?.let { "${it::class.simpleName}: ${it.message}" } ?: ""
            }
        )
    }
}

@Composable
fun Step(
    text: String,
    completed: Boolean,
    failed: Boolean = false,
) = Text(
    text = text,
    modifier = Modifier
        .background(
            when {
                !completed -> Color.LightGray
                completed && failed -> Color.Red
                else -> Color.Green
            }
        )
        .padding(10.dp),
    textAlign = TextAlign.Center
)