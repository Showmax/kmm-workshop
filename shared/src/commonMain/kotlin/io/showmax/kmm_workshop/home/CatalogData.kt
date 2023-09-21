package io.showmax.kmm_workshop.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogData(
    val count: Int,
    val items: List<Item>
)

@Serializable
data class Item(
    val id: String,
    val link: String,
    val images: List<Image>,
)


@Serializable
data class Image(
    val id: String,
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
    SQUARE("square")
}