package com.lifecycle.newsappex.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lifecycle.newsappex.databinding.ActivityMainBinding
import com.lifecycle.newsappex.di.UserRegistrationService
import com.lifecycle.newsappex.models.EmailService
import com.lifecycle.newsappex.models.NewArt
import com.lifecycle.newsappex.viewmodel.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Callable
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val channel = Channel<Int>()

    @Inject
    lateinit var userRegistrationService: UserRegistrationService

    @Inject
    lateinit var emailService: EmailService

    val plants: LiveData<List<NewArt>> = liveData<List<NewArt>> {
//        val plantsLiveData = .getPlants()
//        val customSortOrder = plantsListSortOrderCache.getOrAwait()
//        emitSource(plantsLiveData.map { plantList -> plantList.applySort(customSortOrder) })
    }

    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
//
//        binding.newsListView.layoutManager = LinearLayoutManager(this)
//        binding.newsListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
//        binding.newsListView.adapter = NewsListRecycleAdapter()


        CoroutineScope(Dispatchers.Main).launch {
            withContext(this.coroutineContext) {
                getUserData().forEach {
                    Log.d(TAG, "life life $it")
                }
                producer()
                consumer()
            }

        }

        //rx java
//        simpleObserver()
        createObserver()

        // daggerCode()


        //single oberservable
        singleObservable()
//    fun daggerCode(){
//        val component = DaggerUserRegistrationComponent.builder()
//            .build()
//        component.inject(this)
//    }
    }

    private fun singleObservable() {
        getData()!!.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { data: Data? ->  },
                { error: Throwable? -> })
    }

    class Data

    fun getData(): Observable<Data?>? {
        return Observable.fromCallable<Data?>(Callable<Data?> {
            val result: Data? = null
            result
        })
    }

    private fun createObserver() {
        Observable.create<String> {
            it.onNext("Rajesh")
            it.onNext("Goswami")
            it.onError(Throwable(" something wrong happened"))
            it.onComplete()
        }.subscribe(returnObserver)

    }

    private fun simpleObserver() {
        val list = listOf("namaste", "vanakam", "namaskara")
        val obsrvable = Observable.fromIterable(list)

        obsrvable.subscribe(returnObserver)
    }

    val returnObserver = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            Log.i(TAG, "simple observer called")
        }

        override fun onNext(t: String) {
            Log.i(TAG, t)
        }

        override fun onError(e: Throwable) {
            Log.i(TAG, "got error ${e.printStackTrace()}")
        }

        override fun onComplete() {
            Log.i(TAG, "observer part done")

        }
    }

    private suspend fun consumer() {
        repeat(3) {
            Log.d(TAG, "${channel.receive()}")
        }
    }

    private suspend fun producer() {
        repeat(3) {
            channel.send(it)
        }

    }

    private suspend fun getUserData() = mutableListOf<String>().apply {
        add(getUser(1))
        add(getUser(2))
        add(getUser(3))
        add(getUser(4))
    }


    private suspend fun getUser(id: Int): String {
        delay(1000)
        return "UserID$id"
    }

}