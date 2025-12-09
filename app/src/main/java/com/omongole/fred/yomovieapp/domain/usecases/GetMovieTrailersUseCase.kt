package com.omongole.fred.yomovieapp.domain.usecases

import com.omongole.fred.yomovieapp.data.model.valueObjects.Video
import com.omongole.fred.yomovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<List<Video>> {
        return repository.getMovieTrailers(movieId)
    }
}