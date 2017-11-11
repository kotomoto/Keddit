package com.koto.keddit.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.koto.keddit.R
import com.koto.keddit.models.RedditNewsItem
import com.koto.keddit.extensions.getFriendlyTime
import com.koto.keddit.extensions.inflate
import com.koto.keddit.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*


class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = NewsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item)) {
        fun bind(item: RedditNewsItem) = with(itemView) {
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }
}