package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresViewModel

@Composable
fun GenresScreen(
    modifier: Modifier,
    fetchMoviesByGenre: (genreId: Long, genreName: String) -> Unit,
    fetchShowsByGenre: (genreId: Long, genreName: String) -> Unit
) {
    val genresViewModel: GenresViewModel = hiltViewModel()
    val movieGenres = genresViewModel.moviesGenres.collectAsState().value
    val showsGenres = genresViewModel.showsGenres.collectAsState().value

    if (movieGenres.isEmpty() && showsGenres.isEmpty() && genresViewModel.error.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (genresViewModel.error.isNotEmpty()) {
        NoInternetComponent(
            modifier = modifier.fillMaxSize(),
            error = genresViewModel.error,
            refresh = {
                genresViewModel.error = ""
                genresViewModel.getMovieGenres()
                genresViewModel.getShowsGenres()
            }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            GenreSection(
                title = "Movie Genres",
                genres = movieGenres,
                onGenreClick = fetchMoviesByGenre
            )

            Spacer(modifier = Modifier.height(24.dp))

            GenreSection(
                title = "TV Show Genres",
                genres = showsGenres,
                onGenreClick = fetchShowsByGenre
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreSection(
    title: String,
    genres: List<Genre>,
    onGenreClick: (Long, String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 16.dp),
            textAlign = TextAlign.Start
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(350.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres) { genre ->
                Card(
                    onClick = { onGenreClick(genre.id, genre.name) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = genre.name,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}