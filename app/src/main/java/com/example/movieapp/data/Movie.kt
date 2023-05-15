package com.example.movieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val vote_avarage: Int?,
    val backdrop_path: String?
) {
    fun getIdString(): String {
        return id?.toString() ?: ""
    }

    fun getImageUrl() = "https://image.tmdb.org/t/p/w500/$backdrop_path"

}
