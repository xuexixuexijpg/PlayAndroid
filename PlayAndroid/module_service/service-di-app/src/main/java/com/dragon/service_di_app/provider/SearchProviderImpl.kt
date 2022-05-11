package com.dragon.service_di_app.provider

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main.MainProvider
import com.dragon.ft_main.di.MainScope
import com.dragon.search.SearchProvider
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
                activity.getNavigator().navigateTo(MainFragment::class, navOptions)
        } else throw IllegalArgumentException("Could not navigate")
    }
}