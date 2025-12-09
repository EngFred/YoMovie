package com.omongole.fred.yomovieapp.presentation.screens.shows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.SearchWidget
import com.omongole.fred.yomovieapp.presentation.screens.shows.components.ShowsSection
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsViewModel

@Composable
fun ShowsScreen(
    modifier: Modifier,
    searchShows: (String) -> Unit,
    showDetails: (Int) -> Unit
) {
    val showsViewModel: ShowsViewModel = hiltViewModel()
    val topRatedTvShows = showsViewModel.topRatedTvShows.collectAsLazyPagingItems()
    val popularTvShows = showsViewModel.popularTvShows.collectAsLazyPagingItems()
    val onAirTvShows = showsViewModel.onAirTvShows.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchWidget(
            query = showsViewModel.searchQuery,
            placeHolder = "Search TV Shows...",
            onCloseClicked = { showsViewModel.searchQuery = "" },
            onSearchClicked = {
                if (showsViewModel.searchQuery.isNotEmpty()) {
                    searchShows(showsViewModel.searchQuery)
                }
            },
            onValueChanged = {
                showsViewModel.onEvent(ShowsScreenEvent.SearchQueryChange(it))
            }
        )

        if ((topRatedTvShows.loadState.refresh is LoadState.Error) &&
            (popularTvShows.loadState.refresh is LoadState.Error)
        ) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Offline mode. Check connection.",
                refresh = {
                    topRatedTvShows.refresh()
                    popularTvShows.refresh()
                    onAirTvShows.refresh()
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ShowsSection(
                    shows = onAirTvShows,
                    sectionTitle = "On Air Now",
                    onShowClick = showDetails
                )

                ShowsSection(
                    shows = popularTvShows,
                    sectionTitle = "Trending Shows",
                    onShowClick = showDetails
                )

                ShowsSection(
                    shows = topRatedTvShows,
                    sectionTitle = "Critically Acclaimed",
                    onShowClick = showDetails
                )

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}