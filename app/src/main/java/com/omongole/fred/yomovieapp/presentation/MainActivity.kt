package com.omongole.fred.yomovieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.omongole.fred.yomovieapp.presentation.common.AppBottomBar
import com.omongole.fred.yomovieapp.presentation.navigation.AppNavigationGraph
import com.omongole.fred.yomovieapp.presentation.theme.YoMovieAppTheme
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresMovieResultViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresShowsResultViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.MovieDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.MoviesSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.PlayerScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.SharedViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesGenreAssistedFactory: GenresMovieResultViewModelAssistedFactory

    @Inject
    lateinit var searchAssistedFactory: MoviesSearchResultScreenViewModelAssistedFactory

    @Inject
    lateinit var showsSearchAssistedFactory: ShowsSearchResultScreenViewModelAssistedFactory

    @Inject
    lateinit var movieDetailAssistedFactory: MovieDetailScreenViewModelAssistedFactory

    @Inject
    lateinit var showDetailAssistedFactory: ShowDetailScreenViewModelAssistedFactory

    @Inject
    lateinit var showsResultAssistedFactory: GenresShowsResultViewModelAssistedFactory

    @Inject
    lateinit var moviesPlayerAssistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // We no longer need to collect themeMode since it's forced dark
            YoMovieAppTheme {
                // GLOBAL GRADIENT BACKGROUND
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF0F172A), // Deep Premium Navy
                                    Color(0xFF000000)  // Pure Black
                                )
                            )
                        )
                ) {
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val sharedViewModel: SharedViewModel = viewModel()

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { AppBottomBar(navController = navController) },
        ) { paddingValues ->
            AppNavigationGraph(
                navHostController = navController,
                modifier = Modifier.padding(paddingValues),
                moviesSearchAssistedFactory = searchAssistedFactory,
                moviesGenresAssistedFactory = moviesGenreAssistedFactory,
                showsSearchAssistedFactory = showsSearchAssistedFactory,
                movieDetailAssistedFactory = movieDetailAssistedFactory,
                showDetailAssistedFactory = showDetailAssistedFactory,
                moviesPlayerAssistedFactory = moviesPlayerAssistedFactory,
                sharedViewModel = sharedViewModel,
                darkTheme = true,
                showsGenresAssistedFactory = showsResultAssistedFactory
            )
        }
    }
}