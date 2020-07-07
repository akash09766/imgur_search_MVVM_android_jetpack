package com.axxess.android.axxesschallenge.app.di

import androidx.lifecycle.ViewModel
import com.axxess.android.axxesschallenge.app.viewModel.DetailsActivityViewModel
import com.axxess.android.axxesschallenge.core.di.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsScreenViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsActivityViewModel::class)
    fun bindDetailsActivityViewModel(searchVehicleViewModel: DetailsActivityViewModel): ViewModel
}