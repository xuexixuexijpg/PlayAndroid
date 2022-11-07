package com.dragon.ft_main.navigation


import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_designsystem.icon.Icon
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_main.MainRoute

/**
 * 单页面的主导航图
 */
object MainNavigation : NavigationDestination {
    override val route = "main_route"
    override val destination = "main_destination"
}

fun NavGraphBuilder.mainGraph(
    navigationActions: MainNavigationActions,
    windowSizeClass: WindowSizeClass,
//    nestedGraphs: NavGraphBuilder.() -> Unit,
) {

    composable(route = HomeDestination.route) {
        MainRoute(windowSizeClass = windowSizeClass, navigationActions = navigationActions)
    }

//    nestedGraphs()
}

/**
 * 首页的顶层页
 */
data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
) : NavigationDestination
