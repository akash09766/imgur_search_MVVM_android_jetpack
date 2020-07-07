package com.axxess.android.axxesschallenge.app.dataSource

import com.axxess.android.axxesschallenge.app.api.ApiService
import com.axxess.android.axxesschallenge.app.api.GoogleService
import com.axxess.android.axxesschallenge.app.errorManagement.NetworkError
import com.axxess.android.axxesschallenge.app.errorManagement.NetworkErrorForGoogle
import com.axxess.android.axxesschallenge.app.model.search.ImgurSearchResponse
import com.axxess.android.axxesschallenge.app.room.dao.ImageDetailsDao
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetailsWithComments
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.core.ui.ViewState
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

interface DataRepository {
    fun getGooglePing(): Flow<String>
    fun getImgurSearchData(pageNum: String, searchQuery: String): Flow<ViewState<ImgurSearchResponse>>
    fun  getImageDetailsFromDB(id : String): Flow<ViewState<ImageDetailsWithComments>>
    fun updateImageDetails(imageDetails: ImageDetails): Flow<ViewState<Boolean>>
}

@Singleton
class DefaultDataRepository @Inject constructor(
    private val apiService: ApiService,
    private val googleService: GoogleService,
    private val imageDetailsDao: ImageDetailsDao
    ) : DataRepository {
    val TAG = "DefaultDataRepository"

    override fun getImageDetailsFromDB(imgId : String): Flow<ViewState<ImageDetailsWithComments>> {
        return flow {
            emit(ViewState.loading(true))

            imageDetailsDao.getImagesDetailsWithCommentsList02(imgId)
                .collect { imgDetails ->
                    if (imgDetails != null) {
                        imgDetails.commentsList = imgDetails.commentsList.reversed()
                        emit(ViewState.loading(false))
                        emit(ViewState.success(imgDetails))
                    }
                }
        }.catch { error ->
            emit(ViewState.loading(false))
            emit(ViewState.error(NetworkError(error).getAppErrorMessage()))
        }.flowOn(Dispatchers.IO)
    }

    override fun updateImageDetails(imageDetails: ImageDetails) : Flow<ViewState<Boolean>>{
        return flow {
            imageDetailsDao.insertImagesDetailsWithCommentsList(imageDetails)
            emit(ViewState.success(true))
        }.catch { error ->
            emit(ViewState.error(NetworkError(error).getAppErrorMessage()))
        }.flowOn(Dispatchers.IO)
    }


    override fun getImgurSearchData(
        pageNum: String, searchQuery: String
    ): Flow<ViewState<ImgurSearchResponse>> {
        return flow {
            emit(ViewState.loading(true))
            getGooglePing().collect { response ->
                if (response.contentEquals(MConstants.SUCCESS)) {
                    val imgurSearchResponse =
                        apiService.getImgurSearchData(pageNum = pageNum,searchQuery = searchQuery)
                    if (imgurSearchResponse.success) {
                        emit(ViewState.loading(false))
                        imgurSearchResponse.currentPageNum = pageNum.toInt()
                        emit(ViewState.success(imgurSearchResponse))
                    } else {
                        emit(ViewState.loading(false))
                        emit(ViewState.error(imgurSearchResponse.status.toString()))
                    }
                } else {
                    emit(ViewState.loading(false))
                    emit(ViewState.error(response))
                }
            }
        }.catch { error ->
            emit(ViewState.loading(false))
            emit(ViewState.error(NetworkError(error).getAppErrorMessage()))
        }.flowOn(Dispatchers.IO)
    }

    override fun getGooglePing(): Flow<String> = flow {
        val response = googleService.getGoogle()
        if (response.isSuccessful)
            emit(MConstants.SUCCESS)
    }.catch {
        emit(NetworkErrorForGoogle(it).getAppErrorMessage())
    }.flowOn(Dispatchers.IO)
}

@Module
interface DataRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds
    fun it(it: DefaultDataRepository): DataRepository
}