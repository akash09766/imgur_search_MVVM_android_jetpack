package com.axxess.android.axxesschallenge.core.di

import dagger.Component

@Component(modules = [AppModule::class])
interface ApiComponent {
    fun inject(apiService: NetworkServiceModule)
}