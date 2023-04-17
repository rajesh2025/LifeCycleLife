package com.lifecycle.newsappex.ui.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.lifecycle.newsappex.R
import com.lifecycle.newsappex.adapter.FakeInterface1
import com.lifecycle.newsappex.adapter.FakeInterface2
import com.lifecycle.newsappex.di.DaggerUserRegistrationComponent
import com.lifecycle.newsappex.viewmodel.DashBoardViewModel
import kotlinx.coroutines.flow.collectLatest

class DashboardFragment : Fragment(), FakeInterface1, FakeInterface2 {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DashBoardViewModel::class.java]
        val userRegistrationService =
            DaggerUserRegistrationComponent.builder().build().getUserRegistrationService()
        userRegistrationService.registerUser(password = "helopass", email = "xyz@abc.com")
        updateTimeOutUI()

    }

    private fun updateTimeOutUI() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.isTimeout.collectLatest { timeOutBanner ->
                if (timeOutBanner) {
                    Snackbar.make(
                        this@DashboardFragment.requireView(),
                        "hello ",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Log.d("timeOutBanner","true")
                }else {
                    Snackbar.make(
                        this@DashboardFragment.requireView(),
                        "rajesh ",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Log.d("timeOutBanner","false")
                }
            }
        }
    }

    override fun callMeFirst() {

    }

}