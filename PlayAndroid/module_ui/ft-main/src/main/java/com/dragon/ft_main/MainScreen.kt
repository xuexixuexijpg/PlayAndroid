package com.dragon.ft_main

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.dragon.common_designsystem.component.NiaNavigationBar
import com.dragon.common_designsystem.component.NiaNavigationBarItem
import com.dragon.common_designsystem.component.NiaNavigationRail
import com.dragon.common_designsystem.component.NiaNavigationRailItem
import com.dragon.common_designsystem.icon.Icon
import com.dragon.ft_main.navigation.MainNavHost
import com.dragon.ft_main.navigation.MainNavigationActions
import com.dragon.ft_main.navigation.TopLevelDestination
import com.dragon.ft_main.states.MainState
import com.dragon.ft_main.states.rememberMainState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    navigationActions: MainNavigationActions,
    windowSizeClass: WindowSizeClass,
    mainState: MainState = rememberMainState(windowSizeClass = windowSizeClass)
) {
    Scaffold(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            }
            .fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            if (mainState.shouldShowBottomBar) {
                PlayBottomBar(
                    destinations = mainState.topLevelDestinations,
                    onNavigateToDestination = mainState::navigate,
                    currentDestination = mainState.currentDestination
                )
            }
        }
    ) { padding ->
        Row(
            //https://google.github.io/accompanist/insets/
            Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )

        ) {
            //竖导航
            if (mainState.shouldShowNavRail) {
                NiaNavRail(
                    destinations = mainState.topLevelDestinations,
                    onNavigateToDestination = mainState::navigate,
                    currentDestination = mainState.currentDestination,
                    modifier = Modifier.safeDrawingPadding()
                )
            }

            MainNavHost(
                navController = mainState.navController,
                onBackClick = mainState::onBackClick,
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(padding),
                windowSizeClass = windowSizeClass,
                navigationActions = navigationActions,
                navToMine = mainState::navigateToMine
            )
        }
    }
}

@Composable
private fun NiaNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NiaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )

                        else -> {}
                    }
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

/**
 * 底部导航
 */
@Composable
private fun PlayBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .wrapContentHeight()
            .navigationBarsPadding()
    ) {
        NiaNavigationBar(
            modifier = Modifier.height(50.dp)
        ) {
            destinations.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                //底部导航
                NiaNavigationBarItem(
                    modifier = Modifier.fillMaxHeight(),
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        val icon = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        when (icon) {
                            is Icon.DrawableResourceIcon -> Icon(
                                painter = painterResource(id = icon.id),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(20.dp)
                                    .offset(y = (-6).dp)
                            )
                            else -> {}
                        }
                    },
                    label = {
                        Text(
                            stringResource(destination.iconTextId),
                            modifier = Modifier.offset(y = (10).dp)
                        )
                    }
                )
            }
        }
    }
}