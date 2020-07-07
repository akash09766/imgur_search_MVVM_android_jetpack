package com.axxess.android.axxesschallenge.app.di

import android.app.Application
import com.axxess.android.axxesschallenge.app.application.AxxcessChallengeApplication
import com.axxess.android.axxesschallenge.core.di.GoogleServiceModule
import com.axxess.android.axxesschallenge.core.di.NetworkServiceModule
import com.axxess.android.axxesschallenge.core.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        // Dagger support
        AndroidInjectionModule::class,
        // Global
        AxxessChallengeAppDatabaseModule::class,
        NetworkServiceModule::class,
        GoogleServiceModule::class,
        ViewModelFactoryModule::class,
        // feature
        FeatureBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<AxxcessChallengeApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    override fun inject(newsApp: AxxcessChallengeApplication)
}
