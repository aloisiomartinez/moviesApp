package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.dao.MovieDao
import com.example.movieapp.dataSource.MovieDataSource
import com.example.movieapp.database.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideMovieDao(db: MovieDataBase): MovieDao = db.movieDao(db)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): MovieDataBase =

        Room.databaseBuilder(
            context.applicationContext,
            MovieDataBase::class.java,
            "movie_database"
        ).build()

}