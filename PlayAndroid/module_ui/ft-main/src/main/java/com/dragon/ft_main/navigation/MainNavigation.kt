package com.dragon.ft_main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_main.MainRoute

/**
 * 单页面的主导航图
 */
object MainNavigation : NavigationDestination {
    override val route = "main_route"
    override val destination = "main_destination"
}

fun NavGraphBuilder.mainGraph(
    navigateToHome: (String) -> Unit,
    navigateToMine: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {

    composable(route = MainNavigation.route) {
        MainRoute(navigateToHome = navigateToHome, navigateToMine = navigateToMine)
    }

    nestedGraphs()
}
