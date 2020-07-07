package com.axxess.android.axxesschallenge.app.room.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axxess.android.axxesschallenge.app.room.dao.ImageDetailsDao
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails

@Database(
    entities = [ImageDetails::class,CommentsDetails::class],
    version = AxxcessChallengeDatabaseMigrations.latestVersion
)
abstract class AxxcessChallengeAppDatabase : RoomDatabase() {

    /**
     * Get image and comments Details
     */
    abstract fun imageDetailsDao(): ImageDetailsDao

    companion object {

        private const val databaseName = "axxcess-db"

        fun buildDefault(context: Context) =
            Room.databaseBuilder(context, AxxcessChallengeAppDatabase::class.java, databaseName)
                .addMigrations(*AxxcessChallengeDatabaseMigrations.allMigrations)
                .build()

        @VisibleForTesting
        fun buildTest(context: Context) =
            Room.inMemoryDatabaseBuilder(context, AxxcessChallengeAppDatabase::class.java)
                .build()
    }
}