package com.lifecycle.newsappex.viewmodel

import androidx.lifecycle.*
import com.lifecycle.newsappex.models.NewArt
import kotlinx.coroutines.async

class MainViewModel : ViewModel() {

    val _newArtLiveDataList: LiveData<ArrayList<NewArt>> = MutableLiveData<ArrayList<NewArt>>()
    val newArtLiveDataList: LiveData<ArrayList<NewArt>> = _newArtLiveDataList


    fun loadNewArt(): ArrayList<NewArt>? {
//        applicationContext<Application>().applicationContext
        viewModelScope.async {
//            _newArtLiveDataList.value = loadNewsFromJson()
        }
        return newArtLiveDataList.value
    }
}