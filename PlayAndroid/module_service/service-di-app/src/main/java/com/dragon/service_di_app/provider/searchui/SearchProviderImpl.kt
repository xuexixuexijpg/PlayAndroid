package com.dragon.service_di_app.provider.searchui

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.dragon.search.SearchProvider
import com.dragon.ui_main.constants.RouteConstants
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

//提供主页服务注入
class SearchProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
) : SearchProvider {
    override fun navigateTo(screenName: String, navOptions: FragmentNavigator.NavOptions) {
        if (activity is FragmentNavigator.Transmitter) {
            val nav = activity.getNavigator()
            Log.e("SSSS", ": ${nav.getHistory().count()}" )
            if (nav.canGoBack()) nav.goBack()
            else
                activity.getNavigator().navigateTo(RouteConstants.Main.clazz, navOptions)
        } else throw IllegalArgumentException("Could not navigate")
    }

}