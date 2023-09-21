package io.showmax.kmm_workshop.player

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PlayerData(
    @SerialName("url") val url: List<String>
)