package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.placeholder.PlaceholderContent
import java.util.Date

class MovieViewModel: ViewModel() {

    val movieDetailsLiveData: LiveData<MovieDetailsEnum>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<MovieDetailsEnum>()

    val movieListLiveData: LiveData<MutableList<PlaceholderContent.PlaceholderItem>>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<MutableList<PlaceholderContent.PlaceholderItem>>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    val movieStateStatus: LiveData<DataState>
        get() = _movieStateStatus
    private val _movieStateStatus = MutableLiveData<DataState>()

    init {
        _movieListLiveData.postValue(PlaceholderContent.ITEMS)
    }

    fun onMovieSelected(position: Int) {
        val movieDetails = MovieDetailsEnum(
            title = "Teste nome Filme",
            content = "Teste descrição filme",
            stars = "3"
        )
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToDetailLiveData.postValue(Unit)

        val state = DataState.Error
        setState(state)
    }

    private fun setState(state: DataState) {
        _movieStateStatus.postValue(state)
    }
}