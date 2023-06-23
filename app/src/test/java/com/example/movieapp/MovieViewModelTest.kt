package com.example.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movieapp.data.DataState
import com.example.movieapp.data.Movie
import com.example.movieapp.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    val movieRepository: MovieRepository = mockk()
    val appStateObserver: Observer<DataState> = mockk(relaxed = true)
    val appStateValues = mutableListOf<DataState>()

    lateinit var movieViewModel: MovieViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun { appStateObserver.onChanged(capture(appStateValues)) }

        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Test"))
        movieViewModel = MovieViewModel(movieRepository)

        movieViewModel.appState.observeForever(appStateObserver)
        appStateValues.clear()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        movieViewModel.appState.removeObserver(appStateObserver)

        appStateValues.clear()
    }

    @Test
    fun getMovieData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        coEvery { movieRepository.getMovieData() } returns Result.success(listOf(Movie()))

        movieViewModel.getMovieData()

        Assert.assertEquals(appStateValues, listOf(DataState.Loading, DataState.Success))
    }

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Test"))

        movieViewModel.getMovieData()

        Assert.assertEquals(appStateValues, listOf(DataState.Loading, DataState.Error))
    }

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldEmitList() = runBlocking {
        val list = listOf(Movie())
        coEvery { movieRepository.getMovieData() } returns Result.success(listOf(Movie()))

        movieViewModel.getMovieData()

        Assert.assertEquals(movieViewModel.movieListLiveData.value, list)
    }

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldNotEmitList() = runBlocking {
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Test"))

        movieRepository.getMovieData()

        Assert.assertNull(movieViewModel.movieListLiveData.value)
    }
}