package com.axxess.android.axxesschallenge.core.utils


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axxess.android.axxesschallenge.core.ui.base.BaseActivity
import com.axxess.android.axxesschallenge.core.ui.base.BaseFragment

/**
 * Synthetic sugaring to get instance of [ViewModel] for [AppCompatActivity].
 */
inline fun <reified T : ViewModel> BaseActivity.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory).get(T::class.java)
}

/**
 * Synthetic sugaring to get instance of [ViewModel] for [Fragment].
 */
inline fun <reified T : ViewModel> BaseFragment.getViewModel(): T {
    return ViewModelProvider(this, viewModelFactory).get(T::class.java)
}
