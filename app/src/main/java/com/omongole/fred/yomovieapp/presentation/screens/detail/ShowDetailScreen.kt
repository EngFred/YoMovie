package com.omongole.fred.yomovieapp.presentation.screens.detail
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.common.AnimatedDetailShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.theme.SeaGreen
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowDetailScreenViewModelFactory
import com.omongole.fred.yomovieapp.util.Constants
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
            ShowDetails(show = show, showPoster = showPoster, onBackClick)
        }
    }
}