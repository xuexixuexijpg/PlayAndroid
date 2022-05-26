package com.dragon.ft_main

import androidx.annotation.IdRes
import com.dragon.service_base.navigate.Navigate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kpstv.navigation.BottomNavigationController

interface MainProvider : Navigate {

    /**
     * 三种不同导航设置
     * 1 bottom 2 rail 3 drawer
     */
    fun setNavControlType(types:Int)
}