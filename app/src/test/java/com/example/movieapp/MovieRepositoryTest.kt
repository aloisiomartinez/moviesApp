package com.example.movieapp

import android.util.Log
import com.example.movieapp.data.Movie
import com.example.movieapp.dataSource.MovieApiClienteDataSource
import com.example.movieapp.dataSource.MovieDatabaseDataSource
import com.example.movieapp.repository.MovieRepository

import io.mockk.Matcher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {
    @get:Rule
    val mockKRule = MockKRule(this)

    val movieApiClienteDataSource: MovieApiClienteDataSource = mockk()
    val movieDatabaseDataSource: MovieDatabaseDataSource = mockk()

    val repository = MovieRepository(movieApiClienteDataSource, movieDatabaseDataSource)
    val movieList = listOf(Movie())


    @Before
    fun setup() {
        coEvery{movieDatabaseDataSource.clearData()} returns Unit
        coEvery{movieDatabaseDataSource.saveData(any())} returns Unit
    }

    @Test
    fun getMovieData_whenApiSourceHadSuccess_shouldPersistDataAndReturnList() = runBlocking {
        //Preparação
        val apiResponse = Result.success(movieList)
        coEvery { movieApiClienteDataSource.getMovieData() } returns apiResponse

        //Execução
        val result = repository.getMovieData()

        //Validacão
        Assert.assertEquals(result, apiResponse)
        coVerifySequence {
            movieApiClienteDataSource.getMovieData()
            movieDatabaseDataSource.clearData()
            movieDatabaseDataSource.saveData(movieList)
        }
    }


    @Test
    fun getMovieData_whenApiSourceFailed_shouldLoadLocalData() = runBlocking {
        //Preparação
        val apiResponse = Result.failure<List<Movie>>(Throwable("test"))
        val dbResponse = Result.success(movieList)

        coEvery { movieApiClienteDataSource.getMovieData() } returns apiResponse
        coEvery { movieDatabaseDataSource.getMovieData() } returns dbResponse

        //Execução
        val result = repository.getMovieData()

        coVerify(exactly = 0) {
            movieDatabaseDataSource.clearData()
            movieDatabaseDataSource.saveData(any())
        }
        coVerify(exactly = 1) {
            movieDatabaseDataSource.getMovieData()
        }
        Assert.assertEquals(result, dbResponse)
    }

    @Test
    fun getMovieData_whenApiSourceThrowsExcpetion_shouldReturnLocalData() = runBlocking {
        val dbResponse = Result.success(movieList)

        coEvery { movieApiClienteDataSource.getMovieData() } throws Exception("Test")
        coEvery { movieDatabaseDataSource.getMovieData() } returns dbResponse

        //Execução
        val result = repository.getMovieData()

        coVerify(exactly = 0) {
            movieDatabaseDataSource.clearData()
            movieDatabaseDataSource.saveData(any())
        }
        coVerify(exactly = 1) {
            movieDatabaseDataSource.getMovieData()
        }
        Assert.assertEquals(result, dbResponse)
    }
}