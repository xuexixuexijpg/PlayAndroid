package com.dragon.service_base.navigate

import com.kpstv.navigation.FragmentNavigator

//导航
interface Navigate {
  fun navigateTo(screenName : String,navOptions: FragmentNavigator.NavOptions = FragmentNavigator.NavOptions())
}