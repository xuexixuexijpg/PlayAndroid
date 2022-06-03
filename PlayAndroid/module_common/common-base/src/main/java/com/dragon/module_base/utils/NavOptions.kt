package com.dragon.module_base.utils

import androidx.navigation.NavOptions
import com.dragon.module_base.R

/**
 * 获取不同NavOptions
 *
 * https://blog.csdn.net/stephen_sun_/article/details/123241134
 */

object NavOption{
    val normalNavOption = NavOptions.Builder().apply {
        setLaunchSingleTop(true)
        setRestoreState(true)
        setEnterAnim(R.anim.enter_from_right)
        setPopExitAnim(R.anim.pop_from_right)
    }.build()
}