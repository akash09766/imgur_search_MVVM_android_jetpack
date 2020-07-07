package com.axxess.android.axxesschallenge.core.di

import androidx.lifecycle.ViewModelProvider
import com.axxess.android.axxesschallenge.core.di.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}