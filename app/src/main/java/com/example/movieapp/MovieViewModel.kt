package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.DataState
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieResponse
import com.example.movieapp.movieDetails.MovieDetails
import com.example.movieapp.movieHome.MovieApiService
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel : ViewModel() {

    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    val movieListLiveData: LiveData<List<Movie>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Movie>?>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

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
        val movieDetails = MovieDetails(
            title = "Teste nome Filme",
            content = "Teste descrição filme",
            stars = "3"
        )
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

    private fun getHqData() {
        val authorizationToken = "Bearer ${ApiCredentials().tokenV4}"
        movieService.getComicList(authorizationToken)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i("ResponseAPI", "${response.body()?.results}")
                        _movieListLiveData.postValue(response.body()?.results)
                        _appState.postValue(DataState.Success)
                    } else {
                        _appState.postValue(DataState.Loading)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e("Erro OnFailure GetComicsList", "$call $t")

                    _appState.postValue(DataState.Error)
                }

            })
    }

}