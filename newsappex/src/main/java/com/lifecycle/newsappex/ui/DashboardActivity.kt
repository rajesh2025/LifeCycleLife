package com.lifecycle.newsappex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lifecycle.newsappex.R
import com.lifecycle.newsappex.ui.ui.main.DashboardFragment

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance())
                .commitNow()
        }
    }
}