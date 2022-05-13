package com.dragon.service_di_app.provider.searchui

import androidx.fragment.app.FragmentActivity
import com.dragon.search.SearchProvider
import com.dragon.ui_main.constants.RouteConstants
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

//提供主页服务注入
class SearchProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
) : SearchProvider {
    override fun navigateTo(navOptions: FragmentNavigator.NavOptions) {
        if (activity is FragmentNavigator.Transmitter) {
            val nav = activity.getNavigator()
            if (nav.canGoBack()) nav.goBack()
            else
                activity.getNavigator().navigateTo(RouteConstants.mainPage, navOptions)
        } else throw IllegalArgumentException("Could not navigate")
    }
}