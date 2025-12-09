package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.omongole.fred.yomovieapp.domain.usecases.GetMovieTrailersUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class PlayerUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val videoKey: String? = null
)

class PlayerScreenViewModel @AssistedInject constructor(
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
    @Assisted private val movieId: Int
) : ViewModel() {

    var uiState by mutableStateOf(PlayerUiState())
        private set

    init {
        getTrailers()
    }

    private fun getTrailers() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                getMovieTrailersUseCase(movieId).collectLatest { videos ->
                    if (videos.isNotEmpty()) {
                        val trailer = videos.firstOrNull { it.type == "Trailer" } ?: videos.first()
                        uiState = uiState.copy(
                            isLoading = false,
                            videoKey = trailer.key,
                            error = null
                        )
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = "No trailers found"
                        )
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class PlayerViewModelFactory(
        private val movieId: Int,
        private val assistedFactory: PlayerViewModelAssistedFactory
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(movieId) as T
        }
    }

    @AssistedFactory
    interface PlayerViewModelAssistedFactory {
        fun create(movieId: Int): PlayerScreenViewModel
    }
}