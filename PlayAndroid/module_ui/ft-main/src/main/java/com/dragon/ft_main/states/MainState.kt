package com.dragon.ft_main.states


import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dragon.common_designsystem.icon.Icon
import com.dragon.common_designsystem.icon.PlayIcons
import com.dragon.common_navigation.NavigationDestination
import com.dragon.common_ui.JankMetricDisposableEffect
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_main.navigation.TopLevelDestination
import com.dragon.ft_mine.navigation.MineDestination

@Composable
fun rememberMainState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): MainState {
    NavigationTrackingSideEffect(navController)
    return remember(navController, windowSizeClass) {
        MainState(navController, windowSizeClass)
    }
}

@Stable
class MainState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass
){
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    /**
     * 顶层的页面，主要是防止重新构建导致的内存消耗，一般用于主页
     */
    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            selectedIcon = Icon.DrawableResourceIcon(PlayIcons.selectHomeIcon),
            unselectedIcon = Icon.DrawableResourceIcon(PlayIcons.normalHomeIcon),
            iconTextId = com.dragon.ui.home.R.string.home
        ),
        TopLevelDestination(
            route = MineDestination.route,
            destination = MineDestination.destination,
            selectedIcon = Icon.DrawableResourceIcon(PlayIcons.selectMineIcon),
            unselectedIcon = Icon.DrawableResourceIcon(PlayIcons.normalMineIcon),
            iconTextId = com.dragon.ui.mine.R.string.mine
        )
    )

    fun navigate(destination: NavigationDestination, route: String? = null) {
        trace("Navigation: $destination") {
            if (destination is TopLevelDestination) {
                navController.navigate(route ?: destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.navigate(route ?: destination.route)
            }
        }
    }

    fun navigateToMine(){
        navigate(topLevelDestinations[1])
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    JankMetricDisposableEffect(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}