package com.example.movieapp.dataSource

import android.content.Context
import com.example.movieapp.dao.MovieDao
import com.example.movieapp.data.Movie
import com.example.movieapp.database.MovieDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDatabaseDataSource @Inject constructor(): MovieDataSource {

    @Inject
    lateinit var movieDao: MovieDao


    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            Result.success(loadPersistedMovies())
        }


    override suspend fun saveData(movieList: List<Movie>) {
        movieDao.insertMoviesList(movieList)
    }

    override suspend fun clearData() {
        movieDao.clearMoviesData()
    }

    private suspend fun loadPersistedMovies() = movieDao.getAllMovies()
}