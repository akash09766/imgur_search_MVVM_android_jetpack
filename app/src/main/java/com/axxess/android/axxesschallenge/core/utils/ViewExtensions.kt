package com.axxess.android.axxesschallenge.core.utils



import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.axxess.android.axxesschallenge.core.ui.base.BaseFragment

fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.setVisibilityWithGONE(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        gone()
}

fun View.setVisibilityWithGONE(isVisible: Int) {
    if (isVisible == 1)
        visible()
    else
        gone()
}

fun View.setVisibilityWithINVISIBLE(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        invisible()
}

fun View.invisibleWithOutCheck() {
    visibility = View.INVISIBLE
}

fun View.goneWithOutCheck() {
    visibility = View.GONE
}

fun View.visibleWithOutCheck() {
    visibility = View.VISIBLE
}

fun ImageView.setImageFromRes(resourceId: Int) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context, resourceId
        )
    )
}

fun BaseFragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Fragment.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(requireContext(), colorRes)

/**
 * Easy toast function for Activity.
 */
fun FragmentActivity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

inline fun <reified T : Activity> Activity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}

fun TextView.clearDrawables() {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun TextView.setLeftDrawable(drawableResId: Drawable) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawableResId, null, null, null);
}
fun TextView.setRightDrawable(drawableResId: Drawable) {
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableResId, null);
}