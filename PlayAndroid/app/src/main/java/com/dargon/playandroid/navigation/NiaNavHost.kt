package com.dargon.playandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_home.navigation.homeGraph
import com.dragon.ft_main.navigation.mainGraph
import com.dragon.ft_mine.navigation.MineDestination
import com.dragon.ft_mine.navigation.mineGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun NiaNavHost(
    navController: NavHostController,
    onNavigateToDestination: (NavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        //导航图
        mainGraph(
            navigateToHome = {
                onNavigateToDestination(HomeDestination, HomeDestination.createNavigationRoute(it))
            },
            navigateToMine = {
                onNavigateToDestination(MineDestination, it)
            },
            nestedGraphs = {
                homeGraph()
                mineGraph()
            }
        )
    }
}
