package com.koto.keddit.models

import android.os.Parcel
import android.os.Parcelable
import com.koto.keddit.adapter.AdapterConstants
import com.koto.keddit.adapter.ViewType

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
) : ViewType, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString())

    override fun getViewType() = AdapterConstants.NEWS

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeInt(numComments)
        parcel.writeLong(created)
        parcel.writeString(thumbnail)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<RedditNewsItem> = object : Parcelable.Creator<RedditNewsItem> {
            override fun newArray(size: Int): Array<RedditNewsItem?> = arrayOfNulls(size)

            override fun createFromParcel(source: Parcel) = RedditNewsItem(source)
        }
    }
}