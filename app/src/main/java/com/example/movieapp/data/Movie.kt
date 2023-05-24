package com.example.movieapp.data

import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val vote_average: Float?,
    val backdrop_path: String?
) {
    fun getIdString(): String {
        return id?.toString() ?: ""
    }

    fun getImageUrl() = "https://image.tmdb.org/t/p/w500/$backdrop_path"

    fun getCarouselImages(): List<CarouselItem>? {
        return listOf(
            CarouselItem(
                imageUrl = "https://image.tmdb.org/t/p/w500/$backdrop_path"
            ), CarouselItem(
                imageUrl = "https://image.tmdb.org/t/p/w500/$backdrop_path"
            ), CarouselItem(
                imageUrl = "https://image.tmdb.org/t/p/w500/$backdrop_path"
            ), CarouselItem(
                imageUrl = "https://image.tmdb.org/t/p/w500/$backdrop_path"
            )
        )
    }

}
