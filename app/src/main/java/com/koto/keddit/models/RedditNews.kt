package com.koto.keddit.models

import android.os.Parcel
import android.os.Parcelable
import com.koto.keddit.extensions.createParcel


data class RedditNews(
        val after: String,
        val before: String,
        val news: List<RedditNewsItem>) : Parcelable {

    constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            mutableListOf<RedditNewsItem>().apply {
                parcelIn.readTypedList(this, RedditNewsItem.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = createParcel { RedditNews(it) }
    }
}