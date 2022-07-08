package com.dragon.ft_home.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.PaNavigationDestination

object HomeDestination : PaNavigationDestination {
    override val route: String
        get() = "home_route"
    override val destination: String
        get() = "home_destination"
}

fun NavGraphBuilder.homeGraph( windowSizeClass: WindowSizeClass){
    composable(route=HomeDestination.route){

    }
}