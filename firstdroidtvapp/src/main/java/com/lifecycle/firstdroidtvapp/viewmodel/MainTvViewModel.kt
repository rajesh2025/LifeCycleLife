package com.lifecycle.firstdroidtvapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainTvViewModel constructor(val name : String): ViewModel() {

val  liveData: LiveData<String> by lazy{
     MutableLiveData<String>()
}

    constructor(string: String, age:Int) : this(string)
}