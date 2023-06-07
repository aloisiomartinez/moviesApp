package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@Entity
@JsonClass(generateAdapter = true)
class Movie() {
    @PrimaryKey
    var id: Int? = null
    var title: String? = null
    var overview: String? = null
    var vote_average: Float? = null
    var backdrop_path: String? = null

    @Ignore
    constructor(
         id: Int?,
         title: String?,
         overview: String?,
         vote_average: Float?,
         backdrop_path: String?,
    ): this() {
        this.id = id
        this.title = title
        this.overview = overview
        this.vote_average = vote_average
        this.backdrop_path = backdrop_path
    }


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
