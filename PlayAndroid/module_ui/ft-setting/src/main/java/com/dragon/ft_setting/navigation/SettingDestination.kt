package com.dragon.ft_setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_setting.SettingRoute

object SettingDestination : NavigationDestination {
    override val route: String
        get() = "setting_route"
    override val destination: String
        get() = "setting_destination"
}

fun NavGraphBuilder.settingGraph(){
    composable(route=SettingDestination.route){
        SettingRoute()
    }
}