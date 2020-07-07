package com.axxess.android.axxesschallenge.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axxess.android.axxesschallenge.app.dataSource.DataRepository
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetailsWithComments
import com.axxess.android.axxesschallenge.core.ui.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsActivityViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val TAG = DetailsActivityViewModel::class.java.simpleName
    private lateinit var job: Job

    private var imageDetails = MutableLiveData<ViewState<ImageDetailsWithComments>>()
    val _imageDetails: LiveData<ViewState<ImageDetailsWithComments>>
        get() = imageDetails

    fun getImageDetails(id: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            dataRepository.getImageDetailsFromDB(id)
                .collect { imageDetailsState ->
                    imageDetails.value = imageDetailsState
                }
        }
    }

    fun updateImageDetails(imageDetails: ImageDetails) {
        job = CoroutineScope(Dispatchers.Main).launch {
            dataRepository.updateImageDetails(imageDetails).collect {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) {
            job.cancel()
        }
    }
}