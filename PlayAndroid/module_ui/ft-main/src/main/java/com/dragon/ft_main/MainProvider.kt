package com.dragon.ft_main

import android.os.Parcelable
import com.dragon.module_base.service.navigate.BaseArgs
import com.dragon.module_base.service.navigate.Navigator
import kotlinx.parcelize.Parcelize

interface MainProvider : Navigator<MainArgs> {

    /**
     * 三种不同导航设置
     * 1 bottom 2 rail 3 drawer
     */
    fun setNavControlType(types: Int)
}

@Parcelize
class MainArgs(
    val page: String
) : BaseArgs()
