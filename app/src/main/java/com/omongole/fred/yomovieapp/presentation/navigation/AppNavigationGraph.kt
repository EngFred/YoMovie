package com.omongole.fred.yomovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.omongole.fred.yomovieapp.presentation.screens.PosterScreen
import com.omongole.fred.yomovieapp.presentation.screens.detail.MovieDetailScreen
import com.omongole.fred.yomovieapp.presentation.screens.detail.ShowDetailScreen
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenreShowsResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenresMovieResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenresScreen
import com.omongole.fred.yomovieapp.presentation.screens.home.HomeScreen
import com.omongole.fred.yomovieapp.presentation.screens.player.PlayerScreen
import com.omongole.fred.yomovieapp.presentation.screens.search.MoviesSearchResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.search.SearchScreen
import com.omongole.fred.yomovieapp.presentation.screens.shows.ShowsScreen
import com.omongole.fred.yomovieapp.presentation.screens.shows.ShowsSearchResultScreen
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresMovieResultViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresShowsResultViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.MovieDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.MoviesSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.PlayerScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.SharedViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelAssistedFactory

@Composable
fun AppNavigationGraph(
    navHostController: NavHostController,
    moviesGenresAssistedFactory: GenresMovieResultViewModelAssistedFactory,
    showsGenresAssistedFactory: GenresShowsResultViewModelAssistedFactory,
    moviesSearchAssistedFactory: MoviesSearchResultScreenViewModelAssistedFactory,
    showsSearchAssistedFactory: ShowsSearchResultScreenViewModelAssistedFactory,
    movieDetailAssistedFactory: MovieDetailScreenViewModelAssistedFactory,
    showDetailAssistedFactory: ShowDetailScreenViewModelAssistedFactory,
    moviesPlayerAssistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory,
    modifier: Modifier,
    sharedViewModel: SharedViewModel,
    darkTheme: Boolean
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Home.destination
    ) {

        composable(route= Route.Home.destination ) {
            HomeScreen( modifier = modifier, showMovieDetail = {
                navHostController.navigate("${Route.MovieDetail.destination}/$it")
            },darkTheme = darkTheme )
        }

        composable(route= Route.Search.destination ) {
            SearchScreen(modifier = modifier, searchMovies = {
                navHostController.navigate("${Route.SearchMovies.destination}/$it")
            }, showMovieDetail = {
                navHostController.navigate("${Route.MovieDetail.destination}/$it")
            })
        }

        composable(
            route = "${Route.SearchMovies.destination}/{query}",
            arguments = listOf(
                navArgument("query"){ type = NavType.StringType }
            )
        ) {
            val query = it.arguments?.getString("query")!!
            MoviesSearchResultScreen( query, moviesSearchAssistedFactory, modifier, showMovieDetail = {movieId ->
                navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
            }, showMoviePoster = {posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            }, onBackClicked = {
                navHostController.navigateUp()
            } )
        }

        composable(route= Route.Shows.destination ) {
            ShowsScreen(modifier = modifier, searchShows = {
                navHostController.navigate("${Route.SearchShows.destination}/$it")
            }, showDetails = {showId ->
                navHostController.navigate("${Route.ShowDetail.destination}/$showId")
            })
        }

        composable(
            route = "${Route.SearchShows.destination}/{q}",
            arguments = listOf(
                navArgument(name= "q"){  type = NavType.StringType }
            )
        ){
            val query = it.arguments?.getString("q")!!
            ShowsSearchResultScreen(query = query, assistedFactory = showsSearchAssistedFactory, modifier = modifier, showDetails = {
                navHostController.navigate("${Route.ShowDetail.destination}/$it")
            }, showPoster = {
                sharedViewModel.putPosterPath(it)
                navHostController.navigate(Route.PosterImage.destination)
            }, onBackClicked = {
                navHostController.navigateUp()
            })
        }

        composable(route= Route.Genre.destination ) {
            GenresScreen(modifier = modifier,
                fetchMoviesByGenre = { genreId, genreName ->
                    navHostController.navigate("${Route.MoviesGenreResult.destination}/$genreId/$genreName")
                },
                fetchShowsByGenre = { genreId, genreName ->
                    navHostController.navigate("${Route.ShowsGenreResult.destination}/$genreId/$genreName")
                })
        }

        composable(
            route = "${Route.MoviesGenreResult.destination}/{id}/{name}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.LongType },
                navArgument(name = "name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")!!
            val name = backStackEntry.arguments?.getString("name") ?: "Genre"

            GenresMovieResultScreen(
                genreId = id,
                genreName = name,
                assistedFactory = moviesGenresAssistedFactory,
                showMovieDetail = { movieId ->
                    navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
                },
                showMoviePoster = { posterPath ->
                    sharedViewModel.putPosterPath(posterPath)
                    navHostController.navigate(Route.PosterImage.destination)
                },
                onBackClicked = {
                    navHostController.navigateUp()
                }
            )
        }

        composable(route = Route.PosterImage.destination) {
            PosterScreen(sharedViewModel = sharedViewModel)
        }

        composable(
            route = "${Route.MovieDetail.destination}/{id}",
            arguments = listOf(
                navArgument(name = "id"){ type = NavType.IntType }
            )
        ) {
            val movieId = it.arguments?.getInt("id")!!
            MovieDetailScreen(movieId = movieId, modifier = modifier, assistedFactory = movieDetailAssistedFactory, showMoviePoster = { posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            }, watchVideoPreview = { movieName ->
                navHostController.navigate( "${Route.MoviesPlayer.destination}/$movieName" )
            }, onBackClick = {
                navHostController.navigateUp()
            } )
        }

        composable(
            route = "${Route.MoviesPlayer.destination}/{name}",
            arguments = listOf(
                navArgument(name = "name") { type = NavType.StringType }
            )
        ) {
            val movieName = it.arguments?.getString("name")!!
            PlayerScreen(name = movieName, assistedFactory = moviesPlayerAssistedFactory )
        }

        composable(
            route = "${Route.ShowDetail.destination}/{id}",
            arguments = listOf(
                navArgument(name="id") { type = NavType.IntType }
            )
        ) {
            val showId = it.arguments?.getInt("id")!!
            ShowDetailScreen(showId = showId, modifier = modifier, assistedFactory = showDetailAssistedFactory, showPoster = { posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            }, onBackClick = {
                navHostController.navigateUp()
            } )
        }

        composable(
            route = "${Route.ShowsGenreResult.destination}/{id}/{name}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.LongType },
                navArgument(name = "name") { type = NavType.StringType }
            )
        ) {
            val genreId = it.arguments?.getLong("id")!!
            val genreName = it.arguments?.getString("name") ?: "TV Shows"

            GenreShowsResultScreen(
                assistedFactory = showsGenresAssistedFactory,
                genreId = genreId,
                genreName = genreName,
                showPoster = { posterPath ->
                    sharedViewModel.putPosterPath(posterPath)
                    navHostController.navigate(Route.PosterImage.destination)
                },
                showDetail = { showId ->
                    navHostController.navigate("${Route.ShowDetail.destination}/$showId")
                },
                onBackClicked = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}