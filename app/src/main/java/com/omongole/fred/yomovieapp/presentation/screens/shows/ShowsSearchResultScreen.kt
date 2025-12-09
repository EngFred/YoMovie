package com.omongole.fred.yomovieapp.presentation.screens.shows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelFactory

@Composable
fun ShowsSearchResultScreen(
    query: String,
    assistedFactory: ShowsSearchResultScreenViewModelAssistedFactory,
    modifier: Modifier,
    showDetails: (Int) -> Unit,
    showPoster: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = ShowsSearchResultScreenViewModel::class.java,
        factory = ShowsSearchResultScreenViewModelFactory(query, assistedFactory)
    )

    val shows = viewModel.shows.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
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
                text = "Results for ",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "\"$query\"",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (shows.loadState.refresh is LoadState.Error) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Search failed.",
                refresh = { shows.refresh() }
            )
        } else if (shows.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedSearchResultShimmerEffect()
            }
        }  else {
            // Using Grid instead of List for better UX
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = shows.itemCount,
                    contentType = shows.itemContentType { "shows" }
                ) { index ->
                    shows[index]?.let { show ->
                        PosterImage(
                            imageUrl = show.posterPath,
                            width = null, // Fill grid cell
                            height = 180.dp,
                            scaleType = ContentScale.Crop,
                            id = show.id,
                            onClick = { showDetails(show.id) },
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