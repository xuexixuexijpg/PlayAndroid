package com.dragon.ft_main_home

import androidx.annotation.IdRes
import com.dragon.module_base.service.navigate.BaseArgs
import com.dragon.module_base.service.navigate.Navigator
import kotlinx.parcelize.Parcelize

interface HomeProvider: Navigator<HomeArgs> {

    /**
     *  主要解决在main主页中内部的导航问题
     */
    fun navigateToNative(@IdRes id:Int)
}

@Parcelize
class HomeArgs(
    val page: String
) : BaseArgs()