package com.example.movieapp.dataSource

import com.example.movieapp.api.MovieApiService
import com.example.movieapp.data.ApiCredentials
import com.example.movieapp.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class MovieApiClienteDataSource @Inject constructor() : MovieDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieApiService::class.java)

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            val authorizationToken = "Bearer ${ApiCredentials().tokenV4}"

            val response = movieService.getMovieList(authorizationToken)

            when {
                response.isSuccessful -> Result.success(response.body()?.results)
                else -> Result.failure(Throwable(response.message()))

            }
        }

    override suspend fun saveData(movieList: List<Movie>) {
        // No - OP
    }

    override suspend fun clearData() {
        // No - OP
    }
}