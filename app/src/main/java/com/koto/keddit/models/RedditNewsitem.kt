package com.koto.keddit.models

import android.os.Parcel
import android.os.Parcelable
import com.koto.keddit.adapter.AdapterConstants
import com.koto.keddit.adapter.ViewType
import com.koto.keddit.extensions.createParcel

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
) : ViewType, Parcelable {
    constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt(),
            parcelIn.readLong(),
            parcelIn.readString(),
            parcelIn.readString())

    override fun getViewType() = AdapterConstants.NEWS

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(title)
        dest.writeInt(numComments)
        dest.writeLong(created)
        dest.writeString(thumbnail)
        dest.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = createParcel { RedditNewsItem(it) }
    }
}