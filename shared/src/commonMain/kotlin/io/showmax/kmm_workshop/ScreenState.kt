package io.showmax.kmm_workshop

sealed class ScreenState {
    class Catalog : ScreenState()
    class Detail(val assetUrl: String) : ScreenState()
    class Player(val videoUrl: String) : ScreenState()
}