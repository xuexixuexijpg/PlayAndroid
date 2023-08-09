package com.dargon.playandroid.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dargon.playandroid.ui.PlayAppState
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_main.navigation.MainNavigationActions
import com.dragon.ft_main.navigation.mainGraph
import com.dragon.ft_search.navigation.SearchDestination
import com.dragon.ft_search.navigation.searchGraph
import com.dragon.ft_setting.navigation.SettingDestination
import com.dragon.ft_setting.navigation.settingGraph

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
    appState: PlayAppState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route,
    windowSizeClass: WindowSizeClass,
) {
    val navigationActions = remember(appState) {
        MainNavigationAction(appState)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        //导航图
        mainGraph(navigationActions =navigationActions ,windowSizeClass = windowSizeClass)
        searchGraph()
        settingGraph()
    }
}

/**
 * 首页的导航
 */
class MainNavigationAction(private val appState: PlayAppState): MainNavigationActions {
    override fun navigateToSearch() {
        appState.navigate(SearchDestination)
    }

    override fun navigateToSetting() {
        appState.navigate(SettingDestination)
    }

}
