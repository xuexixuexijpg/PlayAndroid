package com.dragon.ft_mine.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.PaNavigationDestination

object HomeDestination : PaNavigationDestination {
    override val route: String
        get() = "mine_route"
    override val destination: String
        get() = "mine_destination"
}

fun NavGraphBuilder.mineGraph( windowSizeClass: WindowSizeClass){
    composable(route=HomeDestination.route){

    }
}