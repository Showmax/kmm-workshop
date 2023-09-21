package io.showmax.kmm_workshop.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailData(
    val videos: List<Video>,
    val categories: List<Category>,
    val images: List<Image>,
    val description: String,
    val cast: List<Cast>,
    val title: String,
    val year: String
)

@Serializable
data class Video(
    val id: String,
    val usage: VideoUsage,
    val link: String,
)


@Serializable
enum class VideoUsage (val value: String) {
    @SerialName("main")
    MAIN("main"),

    @SerialName("trailer")
    TRAILER("trailer"),

    @SerialName("event")
    EVENT("event")
}

@Serializable
data class Category(
    val title: String
)

@Serializable
data class Image(
    val link: String,
    val orientation: Orientation,
)

@Serializable
enum class Orientation(val value: String) {
    @SerialName("portrait")
    PORTRAIT("portrait"),

    @SerialName("landscape")
    LANDSCAPE("landscape"),

    @SerialName("square")
    SQUARE("square"),
}

@Serializable
data class Cast(
    val name: String
)