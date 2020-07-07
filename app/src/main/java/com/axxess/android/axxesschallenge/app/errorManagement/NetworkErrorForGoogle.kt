package com.axxess.android.axxesschallenge.app.errorManagement

import android.util.Log
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.app.utils.NoConnectivityException
import com.google.gson.JsonParseException
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class NetworkErrorForGoogle(private val throwable: Throwable) : Throwable() {
    val TAG = "NetworkError"

    fun getAppErrorMessage(): String {
        when (throwable) {
            is UnknownHostException -> {
                Log.d(TAG, "throwable is of type UnknownHostException")
                return MConstants.NO_INTERNET
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
                return MConstants.NO_INTERNET
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
            }
            is IllegalStateException -> {
                Log.d(TAG, "throwable is of type IllegalStateException")
                return MConstants.INVALID_SERVER_RESPONSE
            }
            is JsonParseException -> {
                Log.d(TAG, "throwable is of type JsonParseException")
                return MConstants.INVALID_SERVER_RESPONSE
            }
            else -> {
                Log.d(TAG, "throwable is of type Default one")
                return MConstants.TRY_AGAIN
            }
        }
    }
}