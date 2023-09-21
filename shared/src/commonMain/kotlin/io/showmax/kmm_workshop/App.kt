package io.showmax.kmm_workshop

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.showmax.kmm_workshop.detail.showDetailView
import io.showmax.kmm_workshop.home.showHomeView
import io.showmax.kmm_workshop.player.showPlayerView

@Composable
fun App() {
    var actualScreen by remember { mutableStateOf<ScreenState>(ScreenState.Catalog()) }
    MaterialTheme {
        when (actualScreen) {
            is ScreenState.Catalog -> showHomeView(onScreenChange = { newScreen ->
                actualScreen = newScreen
            })

            is ScreenState.Detail -> showDetailView(
                (actualScreen as ScreenState.Detail).assetUrl,
                onScreenChange = { newScreen ->
                    actualScreen = newScreen
                })

            is ScreenState.Player -> showPlayerView(
                (actualScreen as ScreenState.Player).videoUrl
            )
        }
    }
}