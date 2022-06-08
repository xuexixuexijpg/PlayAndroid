package com.dragon.module_base.utils

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.dragon.module_base.R

/**
 * 获取不同NavOptions
 *
 * https://blog.csdn.net/stephen_sun_/article/details/123241134
 */

object NavOption{

    /**
     * 由底部导航栏设置状态保存
     */
    fun popUpSaveStateByRoute( route: String?): NavOptions {
        return NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setRestoreState(true)
            setEnterAnim(R.anim.enter_from_right)
            setPopExitAnim(R.anim.pop_from_right)
            setPopUpTo(route,false,true)
        }.build()
    }

    fun popUpSaveStateById(@IdRes destinationId: Int): NavOptions {
        return NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setRestoreState(true)
            setEnterAnim(R.anim.enter_from_right)
            setPopExitAnim(R.anim.pop_from_right)
            setPopUpTo(destinationId,false,true)
        }.build()
    }
}