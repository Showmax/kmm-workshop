package io.showmax.kmm_workshop.loader

sealed class LoadingState<out DATA>  {
    class Loading : LoadingState<Nothing>()
    class Loaded<out DATA>(val data: DATA) : LoadingState<DATA>()
    class Error(val message: String) : LoadingState<Nothing>()
}