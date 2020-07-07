package com.axxess.android.axxesschallenge.app.di

import androidx.lifecycle.ViewModel
import com.axxess.android.axxesschallenge.app.viewModel.MainActivityViewModel
import com.axxess.android.axxesschallenge.core.di.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchScreenViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainActivityViewModel(searchVehicleViewModel: MainActivityViewModel): ViewModel
}