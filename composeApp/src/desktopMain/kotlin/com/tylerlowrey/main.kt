package com.tylerlowrey

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "frc-kotlin-project-generator",
    ) {
        App()
    }
}