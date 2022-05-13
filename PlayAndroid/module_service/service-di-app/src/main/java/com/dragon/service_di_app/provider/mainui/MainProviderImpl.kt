package com.dragon.service_di_app.provider.mainui

import androidx.fragment.app.FragmentActivity
import com.dragon.ft_main.MainProvider
import com.dragon.ui_main.constants.RouteConstants
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

//提供搜索页服务注入
class MainProviderImpl @Inject constructor(
    private val activity: FragmentActivity
) : MainProvider {
    override fun navigateTo(navOptions: FragmentNavigator.NavOptions) {
        if (activity is FragmentNavigator.Transmitter) {
            activity.getNavigator().navigateTo(RouteConstants.searchPage, navOptions)
        } else throw IllegalArgumentException("Could not navigate")
    }
}