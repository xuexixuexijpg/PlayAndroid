package com.dragon.service_di_app.provider.mainui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dragon.ft_main.MainProvider
import com.dragon.search.SearchFragment
import com.dragon.ui_main.constants.RouteConstants
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

//提供主页服务注入
class MainProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
    private val fragment : Fragment
) : MainProvider {
    override fun navigateTo(screenName: String, navOptions: FragmentNavigator.NavOptions) {
        when(screenName){
            SearchFragment::class.simpleName -> {
                if (activity is FragmentNavigator.Transmitter) {
                    activity.getNavigator().navigateTo(RouteConstants.Search.clazz, navOptions)
                }
            }
        }
    }

}