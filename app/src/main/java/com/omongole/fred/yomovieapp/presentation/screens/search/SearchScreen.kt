package com.omongole.fred.yomovieapp.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.presentation.common.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.SearchWidget
import com.omongole.fred.yomovieapp.presentation.screens.search.components.SearchScreenMovieItem
import com.omongole.fred.yomovieapp.presentation.viewModel.SearchScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.SearchScreenViewModel

@Composable
fun SearchScreen(
    modifier: Modifier,
    searchMovies: (String) -> Unit,
    showMovieDetail: (Int) -> Unit
) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchWidget(
            query = viewModel.searchQuery,
            placeHolder = "Search movies...",
            onCloseClicked = { viewModel.searchQuery = "" },
            onSearchClicked = {
                if (viewModel.searchQuery.isNotEmpty()) {
                    searchMovies(viewModel.searchQuery)
                }
            },
            onValueChanged = {
                viewModel.onEvent(SearchScreenEvent.SearchQueryChange(it))
            }
        )

        when (trendingMovies.loadState.refresh) {
            is LoadState.Error -> {
                NoInternetComponent(
                    modifier = Modifier.fillMaxSize(),
                    error = "Offline mode. Check connection.",
                    refresh = { trendingMovies.refresh() }
                )
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Trending Searches",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            count = trendingMovies.itemCount,
                            contentType = trendingMovies.itemContentType { "trendingMovies" }
                        ) { index ->
                            trendingMovies[index]?.let { movie ->
                                SearchScreenMovieItem(
                                    showMovieDetail = showMovieDetail,
                                    movie = movie
                                )
                            }
                        }

                        if (trendingMovies.loadState.refresh == LoadState.Loading) {
                            item { AnimatedSearchResultShimmerEffect() }
                        }
                    }
                }
            }
        }
    }
}