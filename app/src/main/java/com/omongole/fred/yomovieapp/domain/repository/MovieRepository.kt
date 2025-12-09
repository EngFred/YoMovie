package com.omongole.fred.yomovieapp.domain.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.model.valueObjects.Video
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.domain.model.movies.MovieDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getUpComingMovies() : Flow<PagingData<Movie>>
    fun getNowPlayingMovies() : Flow<PagingData<Movie>>
    fun getPopularMovies() : Flow<PagingData<Movie>>
    fun getTopRatedMovies() : Flow<PagingData<Movie>>
    fun getMoviesGenre() : Flow<List<Genre>>
    fun getMoviesByGenre( genreId: Long ) : Flow<PagingData<Movie>>
    fun getTrendingMovies() : Flow<PagingData<Movie>>
    fun searchMovies( query: String ) : Flow<PagingData<Movie>>
    fun getMovieDetail( movieId: Int ) : Flow<MovieDetail>
    fun getMovieTrailers( movieId: Int ) : Flow<List<Video>>
}