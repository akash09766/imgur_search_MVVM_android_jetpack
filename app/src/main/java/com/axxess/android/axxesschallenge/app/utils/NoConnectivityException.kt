package com.axxess.android.axxesschallenge.app.utils

import java.io.IOException

class NoConnectivityException(val msg: String) : IOException() {
    override val message: String?
        get() = msg
}