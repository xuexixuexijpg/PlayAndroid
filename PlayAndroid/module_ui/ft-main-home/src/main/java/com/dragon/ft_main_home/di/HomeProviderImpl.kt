package com.dragon.ft_main_home.di

import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import com.dragon.ft_main_home.HomeArgs
import com.dragon.ft_main_home.HomeProvider
import javax.inject.Inject

class HomeProviderImpl @Inject constructor():
    HomeProvider {
    override fun navigateTo(navController: NavController, navOptions: HomeArgs?) {

    }


}