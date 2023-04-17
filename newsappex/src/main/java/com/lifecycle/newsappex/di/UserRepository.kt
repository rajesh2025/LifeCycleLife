package com.lifecycle.newsappex.di

import android.util.Log
import com.lifecycle.newsappex.utils.Constants.TAG
import javax.inject.Inject
import kotlin.math.log

class UserRepository @Inject constructor() {

    fun saveUser(email: String, password: String) {
        Log.d(TAG, "user saved")
    }

}
