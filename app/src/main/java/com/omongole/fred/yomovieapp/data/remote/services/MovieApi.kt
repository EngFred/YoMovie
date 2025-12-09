package com.omongole.fred.yomovieapp.data.remote.services


import com.omongole.fred.yomovieapp.data.model.genres.GenresResponse
import com.omongole.fred.yomovieapp.data.model.movies.MovieDetailDTO
import com.omongole.fred.yomovieapp.data.model.movies.MovieResponse
import com.omongole.fred.yomovieapp.data.model.shows.ShowDetailDTO
import com.omongole.fred.yomovieapp.data.model.shows.ShowResponse
import com.omongole.fred.yomovieapp.data.model.tmdb.VideoResponse
import com.omongole.fred.yomovieapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/upcoming?api_key=${API_KEY}")
    suspend fun fetchUpComingMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : MovieResponse

    @GET("movie/now_playing?api_key=${API_KEY}")
    suspend fun fetchNowPlayingMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : MovieResponse

    @GET("movie/popular?api_key=${API_KEY}")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : MovieResponse

    @GET("movie/top_rated?api_key=${API_KEY}")
    suspend fun fetchTopRatedMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : MovieResponse

    @GET("trending/movie/week?api_key=${API_KEY}")
    suspend fun fetchTrendingMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : MovieResponse

    @GET("search/movie?api_key=${API_KEY}")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("query") query: String
    ) : MovieResponse

    @GET("search/tv?api_key=${API_KEY}")
    suspend fun searchShows(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("query") query: String
    ) : ShowResponse

    @GET("movie/{movie_id}?api_key=${API_KEY}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int
    ) : MovieDetailDTO

    @GET("tv/{series_id}?api_key=${API_KEY}")
    suspend fun fetchShowDetail(
        @Path("series_id") showId: Int
    ) : ShowDetailDTO

    @GET("tv/top_rated?api_key=${API_KEY}")
    suspend fun fetchTopRatedTvShows(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : ShowResponse

    @GET("tv/popular?api_key=${API_KEY}")
    suspend fun fetchPopularTvShows(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : ShowResponse

    @GET("tv/airing_today?api_key=${API_KEY}")
    suspend fun fetchOnAirTvShows(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : ShowResponse

    @GET("genre/movie/list?api_key=${API_KEY}")
    suspend fun fetchMoviesGenre() : GenresResponse

    @GET("genre/tv/list?api_key=${API_KEY}")
    suspend fun fetchShowsGenre() : GenresResponse

    @GET("discover/tv?api_key=${API_KEY}")
    suspend fun fetchShowsByGenre(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("with_genres") genreId: Long
    ) : ShowResponse

    @GET("discover/movie?api_key=${API_KEY}")
    suspend fun fetchMoviesByGenre(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("with_genres") genreId: Long
    ) : MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): VideoResponse

}