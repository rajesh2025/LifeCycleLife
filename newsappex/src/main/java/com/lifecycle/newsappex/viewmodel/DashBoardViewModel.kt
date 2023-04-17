package com.lifecycle.newsappex.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val THREE_MINUTES_TIMEOUT = 3L * 60L * 1000L

class DashBoardViewModel : ViewModel() {


    val isTimeout = flow {

        val startTimestamp = 1680342480 * 1000L
        Log.d("startTimestamp ", "$startTimestamp")
        //first time
        emit(checkThreeMinutesTimeout(startTimestamp))

        if (!checkThreeMinutesTimeout(startTimestamp)) {
            val currentTimestamp = System.currentTimeMillis()
            Log.d("currentTimestamp ", "$currentTimestamp")

            Log.d("difference count", "${currentTimestamp - startTimestamp}")
            delay(currentTimestamp - startTimestamp)
            emit(true)
            object : CountDownTimer(currentTimestamp - startTimestamp, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    //not needed
                  //  println("$millisUntilFinished")
                }

                override fun onFinish() {
                    Log.d("CountDownTimer","finsined")
                    viewModelScope.launch {
                        emit(true)
                    }

                }
            }.start()

        }


    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        false
    )


    private fun checkThreeMinutesTimeout(requestedTimeStamp: Long?): Boolean {
        //convert requestedTimeStamp from seconds to milliseconds
        //TODO check the other timestamp format
        val startTimestamp = requestedTimeStamp?.let { it * 1000L } ?: return false
        return isTimerExpired(startTimestamp, THREE_MINUTES_TIMEOUT)
    }

    private fun isTimerExpired(
        startTimestamp: Long?,
        duration: Long,
        currentTimestamp: Long = System.currentTimeMillis()
    ): Boolean = startTimestamp?.let { currentTimestamp - it >= duration } ?: false
}
