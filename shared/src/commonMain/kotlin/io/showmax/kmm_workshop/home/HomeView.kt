package io.showmax.kmm_workshop.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.showmax.kmm_workshop.ScreenState
import io.showmax.kmm_workshop.loader.LoadingState
import io.showmax.kmm_workshop.loader.load
import io.showmax.kmm_workshop.showLoading
import kotlinx.coroutines.launch


@Composable
fun showHomeView(onScreenChange: (ScreenState) -> Unit) {
    val catalogState by load<CatalogData>("https://api.showmax.io/v154.5/android/catalogue/search")

    when (catalogState) {
        is LoadingState.Loading -> {
            showLoading()
        }

        is LoadingState.Error -> {
            Text((catalogState as LoadingState.Error).message)
        }

        is LoadingState.Loaded -> {
            HomeView((catalogState as LoadingState.Loaded).data, onScreenChange)
        }
    }
}

@Composable
private fun HomeView(catalogData: CatalogData, onScreenChange: (ScreenState) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val maxImageSizePath = "/240x240"

    // Image size cap set to 240x240 be returns correct aspect ratio
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(catalogData.count) { index ->

            val painterResource: Resource<Painter> = asyncPainterResource(
                catalogData.items[index].images.first { it.orientation == Orientation.SQUARE }.link + maxImageSizePath,
                filterQuality = FilterQuality.Medium,
            )
            KamelImage(
                resource = painterResource,
                contentDescription = null,
                modifier = Modifier.aspectRatio(1F).padding(8.dp)
                    .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onScreenChange(ScreenState.Detail(assetUrl = catalogData.items[index].link)) },
                contentScale = ContentScale.Crop,
                onLoading = { CircularProgressIndicator(it) },
                onFailure = { exception: Throwable ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = exception.message.toString(),
                            actionLabel = "Hide",
                        )
                    }
                },
            )
        }
    }
}