package com.example.movieapp.api

import com.example.movieapp.data.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiService {
    @GET("list/1")
    suspend fun getMovieList(
        @Header("Authorization") authorization: String,
    ) : Response<MovieResponse>
}