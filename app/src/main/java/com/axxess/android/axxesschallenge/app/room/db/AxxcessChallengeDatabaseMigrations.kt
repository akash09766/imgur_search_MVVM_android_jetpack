package com.axxess.android.axxesschallenge.app.room.db


import androidx.room.migration.Migration

/**
 * Describes migration related to [AxxcessChallengeAppDatabase].
 */
internal object AxxcessChallengeDatabaseMigrations {

    // Bump this on changing the schema
    const val latestVersion = 1

    val allMigrations: Array<Migration>
        get() = arrayOf(
            //// Add migrations here
        )
}