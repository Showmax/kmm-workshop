package io.showmax.kmm_workshop.home

sealed class HomeViewState {
    class Loading : HomeViewState()
    class Loaded(val catalogData: CatalogData) : HomeViewState()
    class Error(val message: String) : HomeViewState()
}