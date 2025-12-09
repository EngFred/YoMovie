package com.omongole.fred.yomovieapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.screens.home.components.AlertDialog
import com.omongole.fred.yomovieapp.presentation.screens.home.components.HeaderSection
import com.omongole.fred.yomovieapp.presentation.screens.home.components.MoviesSection
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    showMovieDetail: (Int) -> Unit,
    darkTheme: Boolean
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    var dialogState by remember { mutableStateOf(false) }

    AlertDialog(
        dialogState = dialogState,
        onOkClicked = { dialogState = false },
        onDismiss = { dialogState = false }
    )

    val nowPlayingMovies = homeScreenViewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = homeScreenViewModel.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeScreenViewModel.topRatedMovies.collectAsLazyPagingItems()

    // Background color set explicitly to ensure theme transition is smooth
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Header stays fixed at top (optional: move inside scroll if you want it to scroll away)
        HeaderSection(
            onClick = { homeScreenViewModel.onEvent(HomeScreenEvent.ThemeToggled(it)) },
            themeMode = darkTheme,
            infoIconClick = { dialogState = true }
        )

        if ((nowPlayingMovies.loadState.refresh is LoadState.Error) &&
            (topRatedMovies.loadState.refresh is LoadState.Error)
        ) {
            NoInternetComponent(
                modifier = Modifier.fillMaxSize(),
                error = "Connection lost. Please check your internet.",
                refresh = {
                    nowPlayingMovies.refresh()
                    topRatedMovies.refresh()
                    popularMovies.refresh()
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // We could add a "Featured" Hero section here in the future

                MoviesSection(
                    movies = nowPlayingMovies,
                    sectionTitle = "Now Playing",
                    onMovieClick = showMovieDetail
                )

                MoviesSection(
                    movies = popularMovies,
                    sectionTitle = "Popular on YoMovie",
                    onMovieClick = showMovieDetail
                )

                MoviesSection(
                    movies = topRatedMovies,
                    sectionTitle = "Top Rated Collections",
                    onMovieClick = showMovieDetail
                )

                Spacer(modifier = Modifier.height(80.dp)) // Extra space at bottom for nav bar
            }
        }
    }
}