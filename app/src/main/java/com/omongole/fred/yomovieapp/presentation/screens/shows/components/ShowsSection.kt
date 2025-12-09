package com.omongole.fred.yomovieapp.presentation.screens.shows.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.presentation.common.AnimatedImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.PosterImage

@Composable
fun ShowsSection(
    modifier: Modifier = Modifier,
    shows: LazyPagingItems<Show>,
    sectionTitle: String,
    onShowClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

//            Text(
//                text = "See All",
//                style = MaterialTheme.typography.labelLarge,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier.clickable { /* TODO: Navigate to list */ }
//            )
        }

        // List
        LazyRow(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (shows.loadState.refresh) {
                is LoadState.Error -> { /* Handle Error */ }
                LoadState.Loading -> {
                    items(5) { AnimatedImageShimmerEffect() }
                }
                is LoadState.NotLoading -> {
                    items(
                        count = shows.itemCount,
                        contentType = shows.itemContentType { "shows" }
                    ) { index ->
                        shows[index]?.let { show ->
                            PosterImage(
                                imageUrl = show.posterPath,
                                width = 140.dp,
                                height = 210.dp,
                                scaleType = ContentScale.Crop,
                                id = show.id,
                                onClick = onShowClick,
                                cornerSize = 12.dp
                            )
                        }
                    }
                }
            }
        }
    }
}