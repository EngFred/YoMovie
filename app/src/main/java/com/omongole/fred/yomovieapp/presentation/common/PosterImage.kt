package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.omongole.fred.yomovieapp.util.displayPosterImage
import kotlinx.coroutines.Dispatchers

@Composable
fun PosterImage(
    imageUrl: String,
    height: Dp = 350.dp,
    width: Dp? = null,
    scaleType: ContentScale = ContentScale.Crop,
    cornerSize: Dp = 12.dp, // Increased default for modern look
    elevation: Dp = 4.dp,
    onClick: ((Int) -> Unit)? = null,
    id: Int? = null
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(displayPosterImage(imageUrl))
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true) // Smooth transition
        .build()

    val modifier = if (width != null) {
        Modifier
            .width(width)
            .height(height)
    } else {
        Modifier
            .fillMaxSize()
            .height(height)
    }

    Card(
        modifier = modifier.clickable { onClick?.invoke(id ?: 0) },
        shape = RoundedCornerShape(cornerSize),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = "Movie Poster",
            contentScale = scaleType,
        )
    }
}