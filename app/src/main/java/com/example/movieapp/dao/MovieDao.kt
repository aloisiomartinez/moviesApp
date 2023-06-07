package com.example.movieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.data.Movie
import com.example.movieapp.database.MovieDataBase

@Dao
abstract class MovieDao(
    private val movieDatabase: MovieDataBase
): BaseDao<Movie> {


    @Transaction
    @Query("SELECT * FROM movie")
    abstract suspend fun getAllMovies(): List<Movie>?

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:id")
    abstract suspend fun getMovieById(id: Int): Movie?

    @Transaction
    @Query("DELETE FROM movie")
    abstract suspend fun clearMoviesData()

    @Transaction
    open suspend fun insertMoviesList(moviesList: List<Movie>) {
        moviesList.forEach { insertMovie(it)}
    }

    @Transaction
    open suspend fun insertMovie(movie: Movie) {
        insert(movie)
    }
}