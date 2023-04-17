package com.lifecycle.newsappex.models

import android.util.Log
import javax.inject.Inject

interface NotificationService {
    fun send(to:String,from:String,body:String)
}


class EmailService @Inject constructor() : NotificationService{
    override fun send(to: String, from: String, body: String) {
        Log.d("","Email sent")
    }
}

class  MessageService(private val retryCount:Int): NotificationService{
    override fun send(to: String, from: String, body: String) {
        Log.d("","Message sent retry Count $retryCount")
    }
}