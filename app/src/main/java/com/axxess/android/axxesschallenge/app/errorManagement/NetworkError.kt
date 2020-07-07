package com.axxess.android.axxesschallenge.app.errorManagement

import android.util.Log
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.app.utils.NoConnectivityException
import com.google.gson.JsonParseException
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import retrofit2.HttpException
import java.net.*
import javax.net.ssl.SSLHandshakeException

class NetworkError(private val throwable: Throwable) : Throwable() {
    val TAG = "NetworkError"

    fun getAppErrorMessage(): String {
        when (throwable) {
            is UnknownHostException -> {
                Log.d(TAG, "throwable is of type UnknownHostException")
                return MConstants.SERVER_NOT_RESPONDING
            }
            is NoConnectivityException -> {
                Log.d(TAG, "throwable is of type NoConnectivityException")
                return MConstants.NOT_CONNECTED_TO_INTERNET
            }
            is ConnectException -> {
                Log.d(TAG, "throwable is of type ConnectException")
                return MConstants.NO_INTERNET
            }
            is SocketTimeoutException -> {
                Log.d(TAG, "throwable is of type SocketTimeoutException")
                return MConstants.SERVER_NOT_RESPONDING
            }
            is ConnectionShutdownException -> {
                Log.d(TAG, "throwable is of type ConnectionShutdownException")
                return MConstants.NO_INTERNET
            }
            is SSLHandshakeException -> {
                Log.d(TAG, "throwable is of type SSLHandshakeException")
                return MConstants.TRY_AGAIN
            }
            is StreamResetException -> {
                Log.d(TAG, "throwable is of type StreamResetException")
                return MConstants.TRY_AGAIN
            }
            is ProtocolException -> {
                Log.d(TAG, "throwable is of type ProtocolException")
                return MConstants.TRY_AGAIN
            }is UnknownServiceException -> {
                Log.d(TAG, "throwable is of type UnknownServiceException")
                return MConstants.TRY_AGAIN
            }
            is IllegalStateException -> {
                Log.d(TAG, "throwable is of type IllegalStateException")
                return MConstants.INVALID_SERVER_RESPONSE
            }
            is JsonParseException -> {
                Log.d(TAG, "throwable is of type JsonParseException")
                return MConstants.INVALID_SERVER_RESPONSE
            }is HttpException -> {
                Log.d(TAG, "throwable is of type HttpException")
                return MConstants.INVALID_SERVER_RESPONSE
            }
            else -> {
                Log.d(TAG, "throwable is of type Default one")
                return MConstants.TRY_AGAIN
            }
        }
    }
}