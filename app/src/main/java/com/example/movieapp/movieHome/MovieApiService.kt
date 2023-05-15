package com.example.movieapp.movieHome

import com.example.movieapp.data.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiService {
    @GET("list/1")
    fun getComicList(
        @Header("Authorization") authorization: String,
    ) : Call<MovieResponse>
}