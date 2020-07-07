package com.axxess.android.axxesschallenge.app.model.search


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImgurDataItem(@SerializedName("cover")
                    val cover: String? = "",
                    @SerializedName("cover_width")
                    val coverWidth: Int = 0,
                    @SerializedName("id")
                    val id: String? = "",
                    @SerializedName("cover_height")
                    val coverHeight: Int = 0,
                    @SerializedName("title")
                    val title: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cover)
        parcel.writeInt(coverWidth)
        parcel.writeString(id)
        parcel.writeInt(coverHeight)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImgurDataItem> {
        override fun createFromParcel(parcel: Parcel): ImgurDataItem {
            return ImgurDataItem(parcel)
        }

        override fun newArray(size: Int): Array<ImgurDataItem?> {
            return arrayOfNulls(size)
        }
    }
}


data class ImgurSearchResponse(@SerializedName("data")
                               var data: ArrayList<ImgurDataItem>?,
                               @SerializedName("success")
                               val success: Boolean = false,
                               @SerializedName("status")
                               var status: Int = 0,
                               var currentPageNum: Int = 0)


