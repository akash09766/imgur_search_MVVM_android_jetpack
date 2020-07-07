package com.axxess.android.axxesschallenge.app.api


import retrofit2.Response
import retrofit2.http.GET

/**
 * ping to google to check if the connected internet is working or not.
 */
interface GoogleService {

    /**
     * ping to google to check if the connected internet is working or not.
     */
    @GET("/")
    suspend fun getGoogle(): Response<String>

}
