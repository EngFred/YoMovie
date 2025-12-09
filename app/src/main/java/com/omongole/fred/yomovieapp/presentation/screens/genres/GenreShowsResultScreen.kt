package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.presentation.common.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.PosterImage
import com.omongole.fred.yomovieapp.presentation.viewModel.GenreShowsResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.GenreShowsResultScreenViewModelFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresShowsResultViewModelAssistedFactory

@Composable
fun GenreShowsResultScreen(
    genreId: Long,
    genreName: String,
    assistedFactory: GenresShowsResultViewModelAssistedFactory,
    showDetail: (Int) -> Unit,
    showPoster: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = GenreShowsResultScreenViewModel::class.java,
        factory = GenreShowsResultScreenViewModelFactory(genreId, assistedFactory)
    )
    val shows = viewModel.shows.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = genreName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Content
        if (shows.loadState.refresh is LoadState.Error) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Connection error",
                refresh = { shows.refresh() }
            )
        } else if (shows.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedSearchResultShimmerEffect()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = shows.itemCount,
                    contentType = shows.itemContentType { "contentType" }
                ) { index ->
                    shows[index]?.let { show ->
                        PosterImage(
                            imageUrl = show.posterPath,
                            width = null,
                            height = 160.dp,
                            scaleType = ContentScale.Crop,
                            id = show.id,
                            onClick = showDetail,
                            cornerSize = 8.dp
                        )
                    }
                }
                if (shows.loadState.append is LoadState.Loading) {
                    item { AnimatedSearchResultShimmerEffect() }
                }
            }
        }
    }
}