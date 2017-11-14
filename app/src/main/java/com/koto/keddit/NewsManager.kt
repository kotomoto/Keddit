package com.koto.keddit

import com.koto.keddit.api.NewsAPI
import com.koto.keddit.models.RedditNews
import com.koto.keddit.models.RedditNewsItem
import com.koto.keddit.restapi.NewsRestAPI
import io.reactivex.Observable


class NewsManager(private val api: NewsAPI = NewsRestAPI()) {
    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body().data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(item.author, item.title, item.numComments, item.created, item.thumbnail, item.url)
                }

                val redditNews = RedditNews(
                        dataResponse.after ?: "",
                        dataResponse.before ?: "",
                        news
                )

                subscriber.onNext(redditNews)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}