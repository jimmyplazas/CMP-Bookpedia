package dev.alejo.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import dev.alejo.bookpedia.app.App
import dev.alejo.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}