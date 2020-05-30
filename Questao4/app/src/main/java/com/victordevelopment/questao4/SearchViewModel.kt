package com.victordevelopment.questao4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData;

class SearchViewModel : ViewModel() {
    // Movie service injection
    private val movieService = MovieService()
    val movieList: MutableLiveData<List<String>> = MutableLiveData()

    fun list() {
        movieList.value = movieService.list()
    }

    fun filter(params: String) {
        movieList.value = movieService.filter(params)
    }

}