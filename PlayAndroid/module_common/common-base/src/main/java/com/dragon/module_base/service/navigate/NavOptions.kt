package com.dragon.module_base.service.navigate

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.dragon.module_base.R

/**
 * 获取不同NavOptions
 *
 * https://blog.csdn.net/stephen_sun_/article/details/123241134
 */

object NavOption {

    /**
     *
     * 导航
     */
    fun navigateOption():NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }


    /**
     * 由底部导航栏设置状态保存
     */
    fun popUpSaveStateByRoute(route: String?): NavOptions {
        return NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setRestoreState(true)
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.slide_in_left)
            setPopExitAnim(R.anim.slide_out_right)
            setPopUpTo(route, false, true)
        }.build()
    }

    fun popUpSaveStateById(@IdRes destinationId: Int): NavOptions {
        return NavOptions.Builder().apply {
            setLaunchSingleTop(true)
            setRestoreState(true)
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.slide_in_left)
            setPopExitAnim(R.anim.slide_out_right)
            setPopUpTo(destinationId, false, true)
        }.build()
    }
}