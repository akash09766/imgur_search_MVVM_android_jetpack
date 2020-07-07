package com.axxess.android.axxesschallenge.app.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails.CommentsDetails.tableName
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails.CommentsDetails.Column

@Entity(tableName = tableName)
data class CommentsDetails(

    /**
     * Primary key for Room.
     */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Column.commentId)
    val commentId: Long?,

    @ColumnInfo(name = Column.comment)
    val comment: String?,

    @ColumnInfo(name = Column.parentImgId)
    val parentImgId: String?

) {
    object CommentsDetails {
        const val tableName = "comment_list_details"

        object Column {
            const val commentId = "comment_id"
            const val comment = "comment"
            const val parentImgId = "img_id"// foreign key
        }
    }
}