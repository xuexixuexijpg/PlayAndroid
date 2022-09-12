package com.dragon.ft_mine.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_mine.MineRoute

object MineDestination : NavigationDestination {
    override val route: String
        get() = "mine_route"
    override val destination: String
        get() = "mine_destination"
}

fun NavGraphBuilder.mineGraph(){
    composable(route=MineDestination.route){
        MineRoute()
    }
}