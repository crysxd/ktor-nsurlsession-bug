package com.test.myapplication

import androidx.compose.ui.window.ComposeUIViewController
import com.test.myapplication.theme.MyApplicationTheme

@Suppress("Unused", "FunctionName")
fun AppUiController() = ComposeUIViewController {
    MyApplicationTheme {
        App()
    }
}