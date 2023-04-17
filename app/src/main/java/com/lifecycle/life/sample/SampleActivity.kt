package com.lifecycle.life.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.lifecycle.life.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.system.measureTimeMillis

open class SampleActivity : AppCompatActivity() {
    private val TAG: String = "SampleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val stateFlow = MutableStateFlow(0)


        lifecycleScope.launch(Dispatchers.IO) {
            Log.i(TAG, measureTimeMillis {
                println(withContextBehaviour(this))
            }.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }



    private suspend fun launchBehaviour(coroutineScope: CoroutineScope){
        val job1 = coroutineScope.launch {
            networkCall1()
        println(this.coroutineContext)
        }
        val job2 = coroutineScope.launch {
            networkCall2()
            println(this.coroutineContext)
        }
        job1.join()
        job2.join()
    }

    private suspend fun asyncBehaviour(coroutineScope: CoroutineScope) {
        val job1 = coroutineScope.async {doLongRunningTaskOne() }
        val job2 = coroutineScope.async { doLongRunningTaskTwo() }
        val combinedResult = job1.await() + job2.await()

    }


    private suspend fun withContextBehaviour(coroutineScope: CoroutineScope) {
        val resultOne = withContext((Dispatchers.IO)) { doLongRunningTaskOne() }
        val resultTwo = withContext(Dispatchers.IO) { doLongRunningTaskTwo() }
        val combinedResult = resultOne + resultTwo

    }

    private suspend fun networkCall1(): String {
        delay(3000L)
        return "Answer1"
    }

    open suspend fun networkCall2(): String {
        delay(3000L)
        return "Answer2"
    }

    private suspend fun doLongRunningTaskOne(): String {
        delay(5000)
        return "One"
    }

    private suspend fun doLongRunningTaskTwo(): String {
        delay(5000)
        return "Two"
    }

}