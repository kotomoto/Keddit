package com.koto.keddit.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.koto.keddit.R
import com.koto.keddit.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item_loading)) {
    }
}