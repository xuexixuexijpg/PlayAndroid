package com.dragon.common_utils.ext

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

/**
 * diff扩展 增加后台线程
 */
fun <T> DiffUtil.ItemCallback<T>.asConfig(isBackground: Boolean = false): AsyncDifferConfig<T> {
    return AsyncDifferConfig.Builder(this).run {
        if (isBackground) {
            setBackgroundThreadExecutor(Dispatchers.IO.asExecutor())
        }
        build()
    }
}