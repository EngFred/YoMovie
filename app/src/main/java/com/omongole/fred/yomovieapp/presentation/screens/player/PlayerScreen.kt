package com.omongole.fred.yomovieapp.presentation.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.PlayerScreenViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun PlayerScreen(
    movieId: Int,
    assistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel = viewModel(
        modelClass = PlayerScreenViewModel::class.java,
        factory = PlayerScreenViewModel.PlayerViewModelFactory(
            movieId, assistedFactory
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when {
            viewModel.uiState.isLoading -> {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }

            viewModel.uiState.error != null -> {
                Text(
                    text = viewModel.uiState.error!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }

            viewModel.uiState.videoKey != null -> {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        YouTubePlayerView(ctx).apply {
                            lifecycleOwner.lifecycle.addObserver(this)
                            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(viewModel.uiState.videoKey!!, 0f)
                                }
                            })
                        }
                    }
                )
            }
        }
    }
}