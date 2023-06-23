package com.example.movieapp

import android.app.Application
import android.provider.ContactsContract.Data
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
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    var movieRepository: MovieRepository
) : ViewModel() {
    
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

    @VisibleForTesting
     fun getMovieData() {

        viewModelScope.launch {
            val movieListResult = movieRepository.getMovieData()

            movieListResult.fold(
                onSuccess = {
                    _movieListLiveData.value = it
                    _appState.value = DataState.Success
                },
                onFailure = {
                    _appState.value = DataState.Error
                }
            )
        }
    }
}

