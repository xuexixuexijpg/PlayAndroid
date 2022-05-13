package com.dragon.common_utils.ext

import android.view.View

/**
 * View相关扩展
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.collapse() {
    this.visibility = View.GONE
}
