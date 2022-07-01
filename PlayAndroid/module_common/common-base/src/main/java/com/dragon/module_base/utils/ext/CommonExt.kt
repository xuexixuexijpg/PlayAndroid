package com.dragon.module_base.utils.ext

import android.text.Html
import android.text.Spanned

/**
 * 转换html字符串
 *
 */
fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}

/**
 * View显示
 *
 */
