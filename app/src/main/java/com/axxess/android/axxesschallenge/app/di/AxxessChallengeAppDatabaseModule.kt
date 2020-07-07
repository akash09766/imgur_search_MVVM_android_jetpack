package com.axxess.android.axxesschallenge.app.di

import android.app.Application
import com.axxess.android.axxesschallenge.app.room.dao.ImageDetailsDao
import com.axxess.android.axxesschallenge.app.room.db.AxxcessChallengeAppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AxxessChallengeAppDatabaseModule {
    @Singleton
    @Provides
    fun provideDb(app: Application): AxxcessChallengeAppDatabase = AxxcessChallengeAppDatabase.buildDefault(app)

    @Singleton
    @Provides
    fun provideTripDetailsDao(db: AxxcessChallengeAppDatabase): ImageDetailsDao = db.imageDetailsDao()
}