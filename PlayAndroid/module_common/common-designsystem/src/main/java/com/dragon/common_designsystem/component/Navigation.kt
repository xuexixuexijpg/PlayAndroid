

package com.dragon.common_designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dragon.common_designsystem.theme.Dark
import com.dragon.common_designsystem.theme.Pink10

/**
 * Now in Android navigation bar item with icon and label content slots. Wraps Material 3
 * [NavigationBarItem].
 */
@Composable
fun RowScope.NiaNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Pink10,
            unselectedIconColor = Dark,
            selectedTextColor = Pink10,
//            unselectedTextColor = NiaNavigationDefaults.navigationContentColor(),
            unselectedTextColor = Dark,
            indicatorColor =  MaterialTheme.colorScheme.surface
        )
    )
}

/**
 * Now in Android navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun NiaNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = NiaNavigationDefaults.NavigationContainerColor,
        contentColor = NiaNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

/**
 * Now in Android navigation rail item with icon and label content slots. Wraps Material 3
 * [NavigationRailItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun NiaNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = NiaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NiaNavigationDefaults.navigationContentColor(),
            selectedTextColor = NiaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NiaNavigationDefaults.navigationContentColor(),
            indicatorColor = NiaNavigationDefaults.navigationIndicatorColor()
        )
    )
}

/**
 * rail with header and content slots. Wraps Material 3 [NavigationRail].
 * [NavigationRailItem]s.
 */
@Composable
fun NiaNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        containerColor = NiaNavigationDefaults.NavigationContainerColor,
        contentColor = NiaNavigationDefaults.navigationContentColor(),
        header = header,
        content = content
    )
}

/**
 * Now in Android navigation default values.
 */
object NiaNavigationDefaults {
    val NavigationContainerColor = Color.Transparent
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
