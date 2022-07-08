package com.dragon.ft_main.di.mainmodule

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import javax.inject.Inject

//提供主页服务注入
class MainProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
) : MainProvider {

    //导航类型
    private var navType = 1
    override fun setNavControlType(types: Int) {
        navType = types
    }

}


