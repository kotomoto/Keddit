package com.koto.keddit.api

import com.koto.keddit.restapi.model.RedditNewsResponse
import retrofit2.Call

interface NewsAPI {
    fun getNews(after: String, limit: String): Call<RedditNewsResponse>
}