package com.koto.keddit.restapi

import com.koto.keddit.api.NewsAPI
import com.koto.keddit.restapi.model.RedditNewsResponse
import retrofit2.Call
import javax.inject.Inject


class NewsRestAPI @Inject constructor(private val redditApi: RedditApi) : NewsAPI {
    override fun getNews(after: String, limit: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}