
package com.dargon.playandroid.ui

import android.graphics.drawable.Icon
import android.inputmethodservice.Keyboard
import android.view.WindowInsets
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.dargon.playandroid.navigation.NiaNavHost
import com.dargon.playandroid.navigation.NiaTopLevelNavigation
import com.dargon.playandroid.navigation.TOP_LEVEL_DESTINATIONS
import com.dargon.playandroid.navigation.TopLevelDestination
import org.w3c.dom.Text

@Composable
fun PlayAndroidApp(windowSizeClass: WindowSizeClass) {
    NiaTheme {
        val navController = rememberNavController()
        val niaTopLevelNavigation = remember(navController) {
            NiaTopLevelNavigation(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NiaBackground {
            Scaffold(
                modifier = Modifier,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
                    ) {
                        NiaBottomBar(
                            onNavigateToTopLevelDestination = niaTopLevelNavigation::navigateTo,
                            currentDestination = currentDestination
                        )
                    }
                }
            ) { padding ->
                Keyboard.Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact &&
                        windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
                    ) {
                        NiaNavRail(
                            onNavigateToTopLevelDestination = niaTopLevelNavigation::navigateTo,
                            currentDestination = currentDestination,
                            modifier = Modifier.safeDrawingPadding()
                        )
                    }

                    NiaNavHost(
                        windowSizeClass = windowSizeClass,
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .consumedWindowInsets(padding)
                    )
                }
            }
        }
    }
}

@Composable
private fun NiaNavRail(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationRail(modifier = modifier) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NiaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToTopLevelDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                        is DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

@Composable
private fun NiaBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        NiaNavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
        ) {

            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NiaNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToTopLevelDestination(destination) },
                    icon = {
                        val icon = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        when (icon) {
                            is ImageVectorIcon -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = null
                            )
                            is DrawableResourceIcon -> Icon(
                                painter = painterResource(id = icon.id),
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}
