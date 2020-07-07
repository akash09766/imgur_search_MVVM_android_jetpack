package com.axxess.android.axxesschallenge.app.application

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AxxcessChallengeApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        // Init DI magic ✨
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}