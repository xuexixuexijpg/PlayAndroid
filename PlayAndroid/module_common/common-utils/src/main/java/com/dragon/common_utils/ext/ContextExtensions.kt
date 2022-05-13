

package com.dragon.common_utils.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.color.MaterialColors

/**
 * context 扩展
 */
fun Context.layoutInflater(): LayoutInflater = LayoutInflater.from(this)

fun Context.colorFrom(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

fun Context.drawableFrom(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Fragment.colorFrom(@ColorRes color: Int) =  ContextCompat.getColor(requireContext(), color)

fun Fragment.drawableFrom(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), id)
}

fun Context.hasThemeColorAttribute(@AttrRes id: Int): Boolean {
    return theme.obtainStyledAttributes(intArrayOf(id))
        .getColor(0, -1) != -1
}

fun Fragment.toPx(dp: Int) : Float = (resources.displayMetrics.density * dp)
fun Context.toPx(dp: Int) : Float = (resources.displayMetrics.density * dp)

@ColorInt
fun Context.getColorAttr(@AttrRes id: Int, @ColorInt fallbackColor: Int = 0): Int {
    return MaterialColors.getColor(this, id, fallbackColor)
}

fun Context.getAttrResourceId(@AttrRes id: Int, typedValue: TypedValue = TypedValue()) : Int {
    theme?.resolveAttribute(id, typedValue, true)
    return typedValue.resourceId
}

fun Context.getRawDataAttr(@AttrRes id: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true) : Int {
    theme.resolveAttribute(id, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.broadcastManager() = LocalBroadcastManager.getInstance(this)

fun Context.findActivityOrNull() : ComponentActivity? {
    if (this is ComponentActivity) return this
    if (this is ContextWrapper) return this.baseContext.findActivityOrNull()
    return null
}

