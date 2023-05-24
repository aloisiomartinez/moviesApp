package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.ApiCredentials
import com.example.movieapp.data.DataState
import com.example.movieapp.data.Event
import com.example.movieapp.data.Movie
import com.example.movieapp.api.MovieApiService
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel : ViewModel() {

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
        getHqData()
    }

    fun onMovieSelected(position: Int) {
        val movieDetails = _movieListLiveData.value?.get(position)
        movieDetails?.let {
            _movieDetailsLiveData.postValue(it)
            _navigationToDetailLiveData.postValue(Event(Unit))
        }
    }

    private fun getHqData() {
        val authorizationToken = "Bearer ${ApiCredentials().tokenV4}"
        viewModelScope.launch {
            val response =  movieService.getComicList(authorizationToken)

            if (response.isSuccessful) {
                Log.i("ResponseAPI", "${response.body()?.results}")
                _movieListLiveData.postValue(response.body()?.results)
                _appState.postValue(DataState.Success)
            } else {
                _appState.postValue(DataState.Loading)
            }
        }
    }

}