package com.lifecycle.newsappex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lifecycle.newsappex.R


class NewsListRecycleAdapter : RecyclerView.Adapter<NewsListRecycleAdapter.NewsModel>() {

    inner class NewsModel(vi:View) : RecyclerView.ViewHolder(vi){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsModel {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.news_listi_tem,
                    parent,
                    false
                )
        return NewsModel(view)
    }

    override fun onBindViewHolder(holder: NewsModel, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}