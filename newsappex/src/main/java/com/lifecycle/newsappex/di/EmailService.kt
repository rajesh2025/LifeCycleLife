package com.lifecycle.newsappex.di

import android.util.Log
import com.lifecycle.newsappex.utils.Constants.TAG
import javax.inject.Inject

class EmailService @Inject constructor() {

    fun send(to: String, from: String, body: String?) {
        Log.d(TAG, "Email sent")
    }

}
