package io.showmax.kmm_workshop.player

import androidx.compose.runtime.Composable

@Composable
expect fun getNativePlayer(videoUrl: String)

expect val encodingPostfix: String
