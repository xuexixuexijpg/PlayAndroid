package com.dargon.playandroid.ui


import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
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
import com.dargon.playandroid.navigation.TopLevelDestination
import com.dragon.common_designsystem.icon.Icon
import com.dragon.common_designsystem.icon.PlayIcons
import com.dragon.common_navigation.NavigationDestination
import com.dragon.common_ui.JankMetricDisposableEffect
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_main.navigation.MainNavigation
import com.dragon.ft_mine.navigation.MineDestination
import com.dragon.playandroid.R

@Composable
fun rememberPlayAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): PlayAppState {
    NavigationTrackingSideEffect(navController)
    return remember(navController, windowSizeClass) {
        PlayAppState(navController, windowSizeClass)
    }
}

@Stable
class PlayAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    /**
     * Top level destinations to be used in the BottomBar and NavRail
     */
    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            selectedIcon = Icon.DrawableResourceIcon(PlayIcons.normalHomeIcon),
            unselectedIcon = Icon.DrawableResourceIcon(PlayIcons.selectHomeIcon),
            iconTextId = R.string.home
        ),
        TopLevelDestination(
            route = MineDestination.route,
            destination = MineDestination.destination,
            selectedIcon = Icon.DrawableResourceIcon(PlayIcons.normalMineIcon),
            unselectedIcon = Icon.DrawableResourceIcon(PlayIcons.selectMineIcon),
            iconTextId = R.string.mine
        )
    )

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination: The [NiaNavigationDestination] the app needs to navigate to.
     * @param route: Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: NavigationDestination, route: String? = null) {
        trace("Navigation: $destination") {
            if (destination is TopLevelDestination) {
                navController.navigate(route ?: destination.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            } else {
                navController.navigate(route ?: destination.route)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

/**
 * Stores information about navigation events to be used with JankStats
 */
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