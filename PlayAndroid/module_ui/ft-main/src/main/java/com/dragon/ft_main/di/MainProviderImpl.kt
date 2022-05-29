package com.dragon.ft_main.di

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import com.dragon.ft_main.MainArgs
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main.MainProvider
import com.dragon.ft_main.R
import com.dragon.ft_main_mine.MineFragment
import javax.inject.Inject

//提供主页服务注入
class MainProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
    private val fragment: Fragment,
) : MainProvider {

    //导航类型
    private var navType = 1
    override fun setNavControlType(types: Int) {
        navType = types
    }

    override fun navigateTo(navController: NavController, navOptions: MainArgs?) {

    }

}


