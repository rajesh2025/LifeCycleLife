package com.lifecycle.firstdroidtvapp.view.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.lifecycle.firstdroidtvapp.R
import com.lifecycle.firstdroidtvapp.view.fragment.ListFragment

class MainTvActivity : FragmentActivity() {

    lateinit var title:TextView
    lateinit var subTitle:TextView
    lateinit var description:TextView
    lateinit var imgBanner:ImageView
    lateinit var listFragment:ListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_tv_activity)
        title = findViewById<TextView>(R.id.title)
        subTitle = findViewById<TextView>(R.id.subtitle)
        description = findViewById<TextView>(R.id.description)
        imgBanner = findViewById<ImageView>(R.id.imgBanner)

        listFragment = ListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment,listFragment)
        transaction.commit()




    }
}