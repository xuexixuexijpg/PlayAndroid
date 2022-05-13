package com.dragon.common_utils.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

@Suppress("DEPRECATION")
fun Activity.applyEdgeToEdgeMode() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
}

val Activity.statusBarHeight : Int
    get() {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

inline fun <reified T : Context> Context.findBaseContext(): T? {
    var ctx: Context? = this
    do {
        if (ctx is T) {
            return ctx
        }
        if (ctx is ContextWrapper) {
            ctx = ctx.baseContext
        }
    } while (ctx != null)

    // If we reach here, there's not an Context of type T in our Context hierarchy
    return null
}

/**
 * 软键盘的相关扩展
 */
fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}