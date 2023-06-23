package com.example.movieapp.di

import com.example.movieapp.api.MovieApiService
import com.example.movieapp.data.ApiCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiCredentials().baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)
}