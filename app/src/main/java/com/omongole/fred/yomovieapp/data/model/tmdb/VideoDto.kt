package com.omongole.fred.yomovieapp.data.model.tmdb

import com.omongole.fred.yomovieapp.data.model.valueObjects.Video

data class VideoDto(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val key: String, // The YouTube ID
    val name: String,
    val site: String, // "YouTube"
    val size: Int,
    val type: String, // "Trailer", "Teaser", "Featurette"
    val official: Boolean,
    val published_at: String
)

fun VideoDto.toVideo(): Video {
    return Video(
        id = id,
        key = key,
        name = name,
        site = site,
        type = type,
        official = official
    )
}