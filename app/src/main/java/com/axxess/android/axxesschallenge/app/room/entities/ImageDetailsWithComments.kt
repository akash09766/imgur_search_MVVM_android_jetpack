package com.axxess.android.axxesschallenge.app.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import kotlin.collections.ArrayList

class ImageDetailsWithComments {
    @Embedded
    var imageDetails: ImageDetails? = null

    @Relation(parentColumn = ImageDetails.ImageDetails.Column.imgId, entityColumn = CommentsDetails.CommentsDetails.Column.parentImgId)
    var commentsList: List<CommentsDetails> = ArrayList()
}