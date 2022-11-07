

package com.dargon.playandroid.navigation

import com.dragon.common_designsystem.icon.Icon
import com.dragon.common_navigation.NavigationDestination

/**
 * 导航路径
 */
data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
): NavigationDestination
