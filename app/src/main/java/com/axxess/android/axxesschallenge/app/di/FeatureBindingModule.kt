package com.axxess.android.axxesschallenge.app.di

import com.axxess.android.axxesschallenge.app.activity.DetailsActivity
import com.axxess.android.axxesschallenge.app.activity.MainActivity
import com.axxess.android.axxesschallenge.app.dataSource.DataRepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Describes list of activities and fragment which require
 * DI.
 *
 * Each [ContributesAndroidInjector] generates a sub-component
 * for each activity under the hood
 */
@Module(
    includes = [
        SearchScreenViewModelModule::class,
        DetailsScreenViewModelModule::class,
        DataRepositoryModule::class
    ]
)
interface FeatureBindingModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeDetailsActivity(): DetailsActivity

}