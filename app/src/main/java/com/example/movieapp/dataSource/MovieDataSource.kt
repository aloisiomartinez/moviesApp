package com.example.movieapp.dataSource

import com.example.movieapp.data.Movie

interface MovieDataSource {
    suspend fun getMovieData(): Result<List<Movie>?>
    suspend fun saveData(movieList: List<Movie>)
    suspend fun clearData()
}