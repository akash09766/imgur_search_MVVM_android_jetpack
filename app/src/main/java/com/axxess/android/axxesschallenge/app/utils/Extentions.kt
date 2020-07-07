package com.axxess.android.axxesschallenge.app.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.axxess.android.axxesschallenge.core.ui.base.BaseActivity
import com.axxess.android.axxesschallenge.core.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.axxess.android.axxesschallenge.R
import java.util.concurrent.TimeUnit

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth =
            context.resources.getDimension(R.dimen.circular_progress_drawable_stroke_width)
        centerRadius =
            context.resources.getDimension(R.dimen.circular_progress_drawable_stroke_radius)
        setColorSchemeColors(ContextCompat.getColor(context, R.color.thumbnail_progress_color))
        start()
    }
}

fun ImageView.loadImage(url: String?) {
    val option = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .placeholder(getProgressDrawable(context))

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .error(this.context.getDrawable(R.drawable.ic_baseline_error_outline))
        .into(this)
}

fun Int.convertSecToMinSec(seconds: Int): String {
    return String.format(
        "%02d min %02d sec",
        (seconds / 3600 * 60 + ((seconds % 3600) / 60)),
        (seconds % 60)
    )
}

fun Int.convertSecToMin(seconds: Int): Int {
    return if (seconds in 40..59)
        1
    else
        (seconds / 60)
}

fun Int.convertMinToMilliSec(minutes: Long): Long {
    return TimeUnit.MINUTES.toMillis(minutes)
}

fun Int.convertSecToMilliSec(seconds: Long): Long {
    if (seconds < 0) {
        return 0
    }
    return TimeUnit.SECONDS.toMillis(seconds)
}

fun Long.convertMilliSecToSec(milliSecs: Long): Long {
    return (milliSecs / 1000)
}

fun String.isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun BaseFragment.showOkaySnackBar(view: View, msg: String) {
    val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(MConstants.SNACK_BAR_OKAY_BTN_TEXT) {
        snackbar.dismiss()
    }
    snackbar.show()
}
fun BaseActivity.showOkaySnackBar(view: View, msg: String) {
    val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(MConstants.SNACK_BAR_OKAY_BTN_TEXT) {
        snackbar.dismiss()
    }
    snackbar.show()
}


fun BaseFragment.showLongSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

fun BaseActivity.showLongSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

fun BaseFragment.showShortSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()

fun BaseActivity.showShortSnackBar(view: View, msg: String) =
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()

fun BaseFragment.showLongToast(msg: String) =
    Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()

fun BaseFragment.showShortToast(msg: String) =
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

fun BaseActivity.showShortToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Resources.isInLandScapeMode(): Boolean =
    this.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            if (editable.toString().isNotEmpty() /*&& (editable.toString().length % 2) == 0*/)
                afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this == 1

fun Float.roundTo(n: Int): Float {
    return "%.${n}f".format(this).toFloat()
}

fun String.returnEmptyStringIfStringIsNullOrBlank(string: String): String {
    return if (string.isNullOrEmpty()) "" else string
}

fun String?.returnEmptyStringIfStringIsNullOrBlankNullable(string: String?): String {
    return if (string.isNullOrEmpty()) "" else string
}

fun String.formatUserNameInitials(userName: String?): String {
    val nameParts: List<String> = userName!!.split(" ")
    val firstName = nameParts[0]
    val firstNameChar = firstName[0]

    return if (nameParts.size > 1) {
        val lastName = nameParts[nameParts.size - 1]
        val lastNameChar = lastName[0]
        (firstNameChar.plus(" ".plus(lastNameChar))).toUpperCase()
    } else {
        firstNameChar.toUpperCase().toString()
    }
}

fun Int.calculateWatchedDurationPercentage(totalDuration: Int, watchedDuration: Int): Int {
    try {
        val percantage = ((watchedDuration * 100) / totalDuration)
        Log.d(
            "Extension File",
            "totalDuration : $totalDuration and watchedDuration : $watchedDuration and percantage : $percantage"
        )
        return percantage
    } catch (e: ArithmeticException) {
        Log.e("Extension File", "ArithmeticException : ${e.message}")
        return 0
    }
}

fun getDeviceUniqueId(context: Context?): String = Settings.Secure.getString(
    context?.getContentResolver(),
    Settings.Secure.ANDROID_ID
) //UUID.randomUUID().toString()


fun getConnectionType(context: Context): ConnectionType {
    var result: ConnectionType =
        ConnectionType.NONE // Returns connection type. 0: none; 1: mobile data; 2: wifi
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = ConnectionType.WIFI
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = ConnectionType.MOBILE
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = ConnectionType.WIFI
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = ConnectionType.MOBILE
                }
            }
        }
    }
//    Log.d("Extension File", "getConnectionType : $result")
    return result
}


fun String.convert24HourTimeTo12HourFormat(timeIn24HourFormat: String): String {
    val timeFormatIn24: DateFormat =
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    val timeFormatIn12: DateFormat =
        SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return timeFormatIn12.format(timeFormatIn24.parse(timeIn24HourFormat))
}

fun String.convertDateTimeToReadableFormat(dateTime: String, dateTimeFormat: String): String {
    val originalDateTimeFormat: DateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val wantToConvertToDateTimeFormat: DateFormat =
        SimpleDateFormat(dateTimeFormat, Locale.ENGLISH)
    return wantToConvertToDateTimeFormat.format(originalDateTimeFormat.parse(dateTime))
}
fun convertDateToReadableFormat(dateTime: Date, dateTimeFormat: String): String {
    val wantToConvertToDateTimeFormat: DateFormat =
        SimpleDateFormat(dateTimeFormat, Locale.ENGLISH)
    return wantToConvertToDateTimeFormat.format(dateTime.time)
}

fun String.isInternetError(): Int {
    if (this == MConstants.TRY_AGAIN) {
        return MConstants.WENT_WRONG_ID
    } else if (this == MConstants.NOT_CONNECTED_TO_INTERNET || this == MConstants.NO_INTERNET) {
        return MConstants.NETWORK_ERROR__ID
    } else {
        return MConstants.BACKEND_ERROR__ID
    }
}