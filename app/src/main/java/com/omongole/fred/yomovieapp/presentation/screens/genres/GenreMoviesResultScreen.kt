package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresMovieResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresMovieResultScreenViewModelFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresMovieResultViewModelAssistedFactory

@Composable
fun GenresMovieResultScreen(
    genreId: Long,
    genreName: String,
    assistedFactory: GenresMovieResultViewModelAssistedFactory,
    showMovieDetail: (Int) -> Unit,
    showMoviePoster: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = GenresMovieResultScreenViewModel::class.java,
        factory = GenresMovieResultScreenViewModelFactory(genreId, assistedFactory)
    )
    val movies = viewModel.movies.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        // Header
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

        if (movies.loadState.refresh is LoadState.Error) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Connection error",
                refresh = { movies.refresh() }
            )
        } else if (movies.loadState.refresh is LoadState.Loading) {
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
                    count = movies.itemCount,
                    contentType = movies.itemContentType { "contentType" }
                ) { index ->
                    movies[index]?.let { movie ->
                        SearchScreenMovieItem(
                            showMovieDetail = showMovieDetail,
                            movie = movie
                        )
                    }
                }
                if (movies.loadState.append is LoadState.Loading) {
                    item { AnimatedSearchResultShimmerEffect() }
                }
            }
        }
    }
}