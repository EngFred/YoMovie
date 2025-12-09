package com.omongole.fred.yomovieapp.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.presentation.common.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.screens.search.components.SearchScreenMovieItem
import com.omongole.fred.yomovieapp.presentation.viewModel.MoviesSearchResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.MoviesSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.MoviesSearchResultScreenViewModelFactory

@Composable
fun MoviesSearchResultScreen(
    query: String,
    assistedFactory: MoviesSearchResultScreenViewModelAssistedFactory,
    modifier: Modifier,
    showMovieDetail: (Int) -> Unit,
    showMoviePoster: (String) -> Unit,
    onBackClicked: () -> Unit,
) {

    val viewModel = viewModel(
        modelClass = MoviesSearchResultScreenViewModel::class.java,
        factory = MoviesSearchResultScreenViewModelFactory(
            query, assistedFactory
        )
    )

    val movies = viewModel.movies.collectAsLazyPagingItems()

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

        if (movies.loadState.refresh is LoadState.Error) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Search failed.",
                refresh = { movies.refresh() }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = movies.itemCount,
                    contentType = movies.itemContentType { "movies" }
                ) { index ->
                    movies[index]?.let { movie ->
                        SearchScreenMovieItem(
                            showMovieDetail = showMovieDetail,
                            movie = movie
                        )
                    }
                }

                if (movies.loadState.append is LoadState.Loading || movies.loadState.refresh is LoadState.Loading) {
                    item { AnimatedSearchResultShimmerEffect() }
                }
            }
        }
    }
}