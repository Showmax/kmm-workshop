package io.showmax.kmm_workshop.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.showmax.kmm_workshop.showLoading
import io.showmax.kmm_workshop.ScreenState
import io.showmax.kmm_workshop.loader.LoadingState
import io.showmax.kmm_workshop.loader.load
import kotlinx.coroutines.launch

@Composable
fun showDetailView(assetUrl: String, onScreenChange: (ScreenState) -> Unit) {
    val loadingState by load<DetailData>(assetUrl)

    when (loadingState) {
        is LoadingState.Loading -> {
           showLoading()
        }

        is LoadingState.Error -> {
            Text((loadingState as LoadingState.Error).message)
        }

        is LoadingState.Loaded -> {
            DetailView((loadingState as LoadingState.Loaded).data, onScreenChange)
        }
    }
}

@Composable
private fun DetailView(data: DetailData, onScreenChange: (ScreenState) -> Unit) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max).height(IntrinsicSize.Max)
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val painterResource: Resource<Painter> = asyncPainterResource(
            data.images.first { it.orientation == Orientation.LANDSCAPE }.link,
            filterQuality = FilterQuality.Medium,
        )
        KamelImage(
            resource = painterResource,
            contentDescription = null,
            modifier = Modifier.aspectRatio(16f / 9f),
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
        Text(modifier = Modifier.padding(8.dp), text = data.title)
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onScreenChange(ScreenState.Player(data.videos.first { it.usage == VideoUsage.TRAILER }.link)) }) {
            Text("Watch Now")
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Genre:")
            Text(data.categories.joinToString(
                separator = ", ", postfix = " " + data.year
            ) { it.title })
        }
        Text(modifier = Modifier.padding(8.dp), text = data.description)

        Row(modifier = Modifier.padding(8.dp)) {
            Text("Cast:")
            Text(data.cast.joinToString(
                separator = ", "
            ) { it.name })
        }
    }
}
