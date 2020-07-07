package com.axxess.android.axxesschallenge.app.room.dao

import androidx.room.*
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetailsWithComments
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDetailsDao {

    @Transaction
    @Query("SELECT * FROM img_details WHERE img_id =:img_id")
    fun getImagesDetailsWithCommentsList02(img_id: String): Flow<ImageDetailsWithComments?>

    @Transaction
    fun insertImagesDetailsWithCommentsList(ImageDetails: ImageDetails) {
        insertCommentsList(ImageDetails.commentsList?.toList())
        insertImageDetails(ImageDetails)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageDetails(ImageDetails: ImageDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommentsList(CommentsDetails: List<CommentsDetails?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommentsList(CommentsDetails: CommentsDetails?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComments(CommentsDetails: List<CommentsDetails?>?): Int

    @Query("DELETE FROM img_details WHERE img_id =:img_id")
    fun deleteImageById(img_id: String)

    @Query("DELETE FROM img_details WHERE img_id IN (:img_id)")
    fun deleteAllCommentsForThatImage(img_id: List<String?>?)

    @Query("DELETE FROM img_details")
    fun nukeImageDetailsTable()

    @Query("DELETE FROM comment_list_details")
    fun nukeEmpListDetailsTable()
}