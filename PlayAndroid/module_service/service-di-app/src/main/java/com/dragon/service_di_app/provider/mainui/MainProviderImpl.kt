package com.dragon.service_di_app.provider.mainui

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main.MainProvider
import com.dragon.ft_main_mine.MineFragment
import com.dragon.search.SearchFragment
import com.dragon.service_di_app.R
import com.dragon.ui_main.MainActivity
import com.dragon.ui_main.constants.RouteConstants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kpstv.navigation.FragmentNavigator
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

    /**
     * 导航控制中心
     */
    override fun navigateTo(screenName: String, navOptions: FragmentNavigator.NavOptions) {
        if (activity is MainActivity) {
            when (screenName) {
                //activity内导航
                SearchFragment::class.simpleName -> {
                    activity.getNavigator().navigateTo(RouteConstants.Search.clazz, navOptions)
                }
                //自己内部导航
                MineFragment::class.simpleName -> {
                    //点击时当前注入的[fragment]是home自己的fragment
                    val mainFragment = activity.getNavigator().getCurrentFragment()
                    if (mainFragment != null && mainFragment is MainFragment) {
                        mainFragment.navTo(navType,R.id.fragment_mine)
                    }
                }
            }
        }
    }
}


