package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.dao.MovieDao
import com.example.movieapp.data.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(movieDatabase: MovieDataBase): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDataBase?= null

        fun getDataBase(context: Context): MovieDataBase {
            // Verifica se o Banco ja foi criado, caso não tenha sido cria um novo banco de dados
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "movie_database"
                ).build()
                this.instance = database
                return database
            }
        }
    }
}