package com.dragon.ft_main_home

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.dragon.module_base.service.navigate.BaseArgs
import com.dragon.module_base.service.navigate.Navigator
import kotlinx.parcelize.Parcelize

interface HomeProvider: Navigator<HomeArgs> {

    /**
     *  主要解决在main主页中内部的导航问题
     */
    fun navigateToNative(@IdRes id:Int)

    /**
     * 在activity中从mainFragment导航到其他页面
     */
    fun navigateToPage(@IdRes id:Int = 0, routePath:String?=null, navOptions: NavOptions?=null, args: Bundle?=null)
}

@Parcelize
class HomeArgs(
    val page: String
) : BaseArgs()