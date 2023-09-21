package io.showmax.kmm_workshop.player

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import io.showmax.kmm_workshop.loader.LoadingState
import io.showmax.kmm_workshop.loader.load
import io.showmax.kmm_workshop.showLoading

@Composable
fun showPlayerView(videoUrl: String) {
    val playerState by load<PlayerData>(videoUrl + encodingPostfix)

    when (playerState) {
        is LoadingState.Loading -> {
            showLoading()
        }

        is LoadingState.Error -> {
            Text((playerState as LoadingState.Error).message)
        }

        is LoadingState.Loaded -> {
            getNativePlayer((playerState as LoadingState.Loaded).data.url.first())
        }
    }
}