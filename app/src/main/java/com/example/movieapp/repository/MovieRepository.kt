package com.example.movieapp.repository

import android.content.Context
import com.example.movieapp.data.Movie
import com.example.movieapp.dataSource.MovieApiClienteDataSource
import com.example.movieapp.dataSource.MovieDatabaseDataSource
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(
    var movieApiClienteDataSource: MovieApiClienteDataSource,
    var movieDataBaseDataSource: MovieDatabaseDataSource
) {

    suspend fun getMovieData(): Result<List<Movie>?> {
        return try {
            val result = movieApiClienteDataSource.getMovieData()

            if (result.isSuccess) {
                persistData(result.getOrNull())
                result
            } else {
                getLocalData()
            }

        } catch (e: Exception) {
            getLocalData()
        }
    }

    private suspend fun persistData(movieList: List<Movie>?) {
        movieDataBaseDataSource.clearData()
        movieList?.let {
            movieDataBaseDataSource.saveData(it)
        }
    }

    private suspend fun getLocalData() = movieDataBaseDataSource.getMovieData()

}