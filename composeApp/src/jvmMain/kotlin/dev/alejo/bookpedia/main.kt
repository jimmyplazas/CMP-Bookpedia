package dev.alejo.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.alejo.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            App()
        }
    }
}