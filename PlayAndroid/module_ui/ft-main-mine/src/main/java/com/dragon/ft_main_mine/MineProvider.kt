package com.dragon.ft_main_mine

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions

interface MineProvider {
    /**
     * 在activity中从mainFragment导航到其他页面
     */
    fun navigateToPage(@IdRes id:Int = 0, routePath:String?=null, navOptions: NavOptions?=null, args: Bundle?=null)

}