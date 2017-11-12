package com.koto.keddit.restapi.model


class RedditDataResponse(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
) {
}