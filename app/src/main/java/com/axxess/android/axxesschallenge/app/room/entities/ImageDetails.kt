package com.axxess.android.axxesschallenge.app.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails.ImageDetails.tableName
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails.ImageDetails.Column

@Entity(tableName = tableName)
data class ImageDetails(
    /**
     * Primary key for Room.
     */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Column.imgId)
    val imgId: String,

    @ColumnInfo(name = Column.imgTitle)
    val imgTitle: String?
) {
    @Ignore
    var commentsList: ArrayList<CommentsDetails?>? = ArrayList()

    object ImageDetails {
        const val tableName = "img_details"

        object Column {
            const val imgId = "img_id"
            const val imgTitle = "img_title"
        }
    }
}