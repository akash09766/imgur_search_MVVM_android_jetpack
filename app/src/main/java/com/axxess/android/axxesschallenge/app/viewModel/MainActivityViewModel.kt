package com.axxess.android.axxesschallenge.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axxess.android.axxesschallenge.app.dataSource.DataRepository
import com.axxess.android.axxesschallenge.app.model.search.ImgurSearchResponse
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.core.ui.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName
    private lateinit var job: Job

    private var imgurSearchResponse = MutableLiveData<ViewState<ImgurSearchResponse>>()
    val _imgurSearchResponse: LiveData<ViewState<ImgurSearchResponse>>
        get() = imgurSearchResponse

    var isReachedToEndOfList = false

    var prevPageNum = MConstants.PAGINATION_INITIAL_PAGE_NUMBER
    var nextPageNumber = MConstants.START_PAGE_NUMBER
    var prevSearchQuery = ""

    private var listData = ImgurSearchResponse(data = arrayListOf())

    fun getImgurSearchData(pageNum: String, searchQuery: String) {
        var copyOfPageNumber = pageNum
        val isQueryUnModified = searchQuery.equals(prevSearchQuery,ignoreCase = false)

        if (!isQueryUnModified) {
            prevPageNum = MConstants.PAGINATION_INITIAL_PAGE_NUMBER
            nextPageNumber = MConstants.START_PAGE_NUMBER
            copyOfPageNumber = MConstants.START_PAGE_NUMBER.toString()
        } else if (copyOfPageNumber.toInt() <= prevPageNum) {
            Log.d(TAG, "getImgurSearchData() caching....")
            return
        }

        Log.d(TAG, "getImgurSearchData() first request")

        job = CoroutineScope(Dispatchers.Main).launch {
            dataRepository.getImgurSearchData(pageNum = copyOfPageNumber, searchQuery = searchQuery)
                .collect { searchDataState ->

                    when (searchDataState) {
                        is ViewState.Success -> {
                            prevSearchQuery = searchQuery
                            isReachedToEndOfList = searchDataState.data.data.isNullOrEmpty()

                            if (!isReachedToEndOfList) {
                                nextPageNumber++
                            }

                            prevPageNum = searchDataState.data.currentPageNum

                            if(!isQueryUnModified){
                                listData.data?.clear()
                            }

                            searchDataState.data.data?.let { listData.data?.addAll(it) }
                            listData.currentPageNum = searchDataState.data.currentPageNum
                            listData.status = searchDataState.data.status

                            imgurSearchResponse.value = ViewState.success(listData)
                        }
                        is ViewState.Loading -> {
                            imgurSearchResponse.value =
                                ViewState.loading(searchDataState.data)
                        }
                        is ViewState.Error -> {
                            imgurSearchResponse.value =
                                ViewState.Error(searchDataState.message)
                        }
                    }
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