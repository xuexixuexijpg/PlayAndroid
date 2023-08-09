package com.dragon.ft_home.navigation

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dragon.common_navigation.NavigationDestination
import com.dragon.ft_home.HomeRoute

object HomeDestination : NavigationDestination {
    const val topicIdArg = "HomeId"

    override val route: String
        get() = "home_route"
    override val destination: String
        get() = "home_destination"

    /**
     * Creates destination route for a topicId that could include special characters
     */
    fun createNavigationRoute(topicIdArg: String): String {
        val encodedId = Uri.encode(topicIdArg)
        return "home_route/$encodedId"
    }

    /**
     * Returns the topicId from a [NavBackStackEntry] after a topic destination navigation call
     */
    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments?.getString(topicIdArg)!!
        return Uri.decode(encodedId)
    }
}

/**
 * 导航图
 */
fun NavGraphBuilder.homeGraph(navigateToSearch: () -> Unit,
                              navigateToHome: () -> Unit,
                              navigateToSetting: () -> Unit
) {
    composable(route = HomeDestination.route) {
        HomeRoute(navigateToSearch = navigateToSearch,
            navigateToHome = navigateToHome,
            navigateToSetting = navigateToSetting
            )
    }
}