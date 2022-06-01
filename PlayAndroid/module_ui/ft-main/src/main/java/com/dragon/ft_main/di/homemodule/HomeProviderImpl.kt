package com.dragon.ft_main.di.homemodule

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main.di.MainProviderImpl
import com.dragon.ft_main_home.HomeArgs
import com.dragon.ft_main_home.HomeProvider
import javax.inject.Inject

class HomeProviderImpl @Inject constructor(
    private val fragment: Fragment
):
    HomeProvider {

    override fun navigateToNative(@IdRes id: Int) {
        val parentFragment = fragment.parentFragment?.parentFragment
        if (parentFragment != null && parentFragment is MainFragment){
            parentFragment.bottomItemSelect(id)
        }
    }

    override fun navigateTo(navController: NavController, navOptions: HomeArgs?) {

    }
}