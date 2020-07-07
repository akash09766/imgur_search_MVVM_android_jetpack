package com.axxess.android.axxesschallenge.app.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    private val TAG = "NetworkConnectionInterc"

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException(MConstants.NOT_CONNECTED_TO_INTERNET)
            // Throwing our custom exception 'NoConnectivityException'
        }

        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("Authorization",FsConfig.CLIENT_ID)
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        return getConnectionType(context) != ConnectionType.NONE
    }
}