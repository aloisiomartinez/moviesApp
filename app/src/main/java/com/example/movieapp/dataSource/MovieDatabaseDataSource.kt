package com.example.movieapp.dataSource

import android.content.Context
import com.example.movieapp.data.Movie
import com.example.movieapp.database.MovieDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDatabaseDataSource(
    context: Context
) : MovieDataSource {

    private val movieDatabase = MovieDataBase.getDataBase(context)
    private val movieDao = movieDatabase.movieDao(movieDatabase)

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