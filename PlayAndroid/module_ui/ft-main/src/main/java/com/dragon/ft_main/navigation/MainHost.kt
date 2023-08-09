package com.dragon.ft_main.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dragon.ft_home.navigation.HomeDestination
import com.dragon.ft_home.navigation.homeGraph
import com.dragon.ft_mine.navigation.mineGraph

@Composable
fun MainNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route,
    windowSizeClass: WindowSizeClass,
    navigationActions: MainNavigationActions,
    navToMine: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        //从首页跳到搜索页重组了一次，返回之后该导航页重组了两次??
        homeGraph(
            { navigationActions.navigateToSearch() }, //导航到搜索页
            { navToMine() }) //导航到我的,
        { navigationActions.navigateToSetting() }
        mineGraph()

    }
}