package com.dragon.ft_search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_search.SearchRoute

object SearchDestination : NavigationDestination {
    override val route: String
        get() = "search_route"
    override val destination: String
        get() = "search_destination"
}

fun NavGraphBuilder.searchGraph(){
    composable(route=SearchDestination.route){
        SearchRoute()
    }
}