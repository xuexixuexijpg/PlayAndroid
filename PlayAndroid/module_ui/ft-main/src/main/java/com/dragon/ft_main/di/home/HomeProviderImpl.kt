package com.dragon.ft_main.di.home

import com.dragon.ft_main.MainProvider
import com.dragon.ft_main_home.HomeProvider
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

class HomeProviderImpl @Inject constructor(private val mainProvider: MainProvider):
    com.dragon.ft_main_home.HomeProvider {
    override fun navigateTo(screenName: String, navOptions: FragmentNavigator.NavOptions) {
        mainProvider.navigateTo(screenName,navOptions)
    }
}