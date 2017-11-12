package com.koto.keddit.restapi.model


class RedditNewsDataResponse(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
)