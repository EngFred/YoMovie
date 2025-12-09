package com.omongole.fred.yomovieapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.omongole.fred.yomovieapp.data.mapper.toGenre
import com.omongole.fred.yomovieapp.data.mapper.toMovie
import com.omongole.fred.yomovieapp.data.mapper.toMovieDetail
import com.omongole.fred.yomovieapp.data.model.tmdb.toVideo
import com.omongole.fred.yomovieapp.data.model.valueObjects.Video
import com.omongole.fred.yomovieapp.data.remote.services.MovieApi
import com.omongole.fred.yomovieapp.data.remote.source.movies.MoviesByGenrePagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.NowPlayingMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.PopularMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.SearchMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.TopRatedMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.TrendingMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.UpComingMoviesPagingSource
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.domain.model.movies.MovieDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.repository.MovieRepository
import com.omongole.fred.yomovieapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {

    companion object {
        const val TAG = "MovieRepositoryImpl"
    }

    override fun getUpComingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { UpComingMoviesPagingSource( movieApi ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { NowPlayingMoviesPagingSource( movieApi ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { PopularMoviesPagingSource( movieApi ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMoviesGenre(): Flow<List<Genre>> {
        return flow {
            emit( movieApi.fetchMoviesGenre().genres.map { it.toGenre()} )
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, "$it")
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> {
        return flow {
            emit( movieApi.fetchMovieDetail(movieId).toMovieDetail() )
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, "$it")
        }
    }

    override fun searchMovies( query: String ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { SearchMoviesPagingSource( movieApi, query ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { TrendingMoviesPagingSource(movieApi) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMoviesByGenre( genreId: Long ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { MoviesByGenrePagingSource( movieApi, genreId ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { TopRatedMoviesPagingSource( movieApi ) },
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieTrailers(movieId: Int): Flow<List<Video>> {
        return flow {
            val videos = movieApi.getMovieVideos(movieId).results
                .map { it.toVideo() }
                .filter { it.site == "YouTube" }
            emit(videos)
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "Error fetching trailers: $it")
            emit(emptyList())
        }
    }
}