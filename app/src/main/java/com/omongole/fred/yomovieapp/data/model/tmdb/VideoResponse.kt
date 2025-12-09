package com.omongole.fred.yomovieapp.data.model.tmdb


data class VideoResponse(
    val id: Int,
    val results: List<VideoDto>
)