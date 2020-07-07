package com.axxess.android.axxesschallenge.app.api

import com.axxess.android.axxesschallenge.app.model.search.ImgurSearchResponse
import com.axxess.android.axxesschallenge.app.utils.FsConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * List of API Calls.
 */
interface ApiService {

    /**
     * get imgur search data .
     */
    @GET(FsConfig.SEARCH_API)
    suspend fun getImgurSearchData(@Path("page")  pageNum: String,
                                    @Query("q") searchQuery: String
    )
            : ImgurSearchResponse
}
