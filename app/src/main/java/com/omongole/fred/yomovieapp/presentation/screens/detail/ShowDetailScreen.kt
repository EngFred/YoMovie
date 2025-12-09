package com.omongole.fred.yomovieapp.presentation.screens.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.theme.SeaGreen
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelFactory
import com.omongole.fred.yomovieapp.util.Resource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDetailScreen(
    showId: Int,
    modifier: Modifier,
    assistedFactory: ShowDetailScreenViewModelAssistedFactory,
    showPoster: (String) -> Unit,
    onBackClick: () -> Unit
) {

    val viewModel =  viewModel(
        modelClass = ShowDetailScreenViewModel::class.java,
        factory = ShowDetailScreenViewModelFactory(
            showId, assistedFactory
        )
    )

    val showDetailState = viewModel.show.collectAsState().value

    when( showDetailState ) {

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator( modifier = Modifier.size(50.dp), color = SeaGreen )
            }
        }
        is Resource.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoInternetComponent(modifier = modifier, error = showDetailState.message, refresh = {
                    viewModel.getShowDetail(showId)
                } )
            }
        }
        is Resource.Success -> {
            val show = showDetailState.result
            ShowDetails(show = show, showPoster = showPoster, onBackClick = onBackClick)
        }
    }
}