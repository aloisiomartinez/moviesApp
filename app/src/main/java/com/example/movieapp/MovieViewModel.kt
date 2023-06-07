package com.example.movieapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.ApiCredentials
import com.example.movieapp.data.DataState
import com.example.movieapp.data.Event
import com.example.movieapp.data.Movie
import com.example.movieapp.api.MovieApiService
import com.example.movieapp.database.MovieDataBase
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDatabase = MovieDataBase.getDataBase(application)
    private val movieDao = movieDatabase.movieDao(movieDatabase)

    val movieDetailsLiveData: LiveData<Movie>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<Movie>()

    val movieListLiveData: LiveData<List<Movie>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Movie>?>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieApiService::class.java)


    init {
        _appState.postValue(DataState.Loading)
        getMovieData()
    }

    fun onMovieSelected(position: Int) {
        val movieDetails = _movieListLiveData.value?.get(position)
        movieDetails?.let {
            _movieDetailsLiveData.postValue(it)
            _navigationToDetailLiveData.postValue(Event(Unit))
        }
    }

    private fun getMovieData() {
        val authorizationToken = "Bearer ${ApiCredentials().tokenV4}"
        viewModelScope.launch {
            try {
                val response = movieService.getMovieList(authorizationToken)

                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    movies?.let {
                        persistMovies(it)
                    }

                    _movieListLiveData.postValue(movies)
                    _appState.postValue(DataState.Success)
                } else {
                    errorHandling()
                }
            } catch (e: Exception) {
                errorHandling()
            }
        }
    }

    private suspend fun errorHandling() {
        val moviesList = loadPersistedMovies()

        if (moviesList.isNullOrEmpty()) {
            _appState.postValue(DataState.Loading)
        } else {
            _movieListLiveData.postValue(moviesList)
            _appState.postValue(DataState.Loading)
        }

    }

    private suspend fun persistMovies(movieList: List<Movie>) {
        movieDao.clearMoviesData()
        movieDao.insertMoviesList(movieList)
    }

    private suspend fun loadPersistedMovies() = movieDao.getAllMovies()
}