

package com.dragon.common_designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Now in Android toggle button with icon and checked icon content slots. Wraps Material 3
 * [IconButton].
 *
 */
@Composable
fun PlayToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon,
    size: Dp = PlayToggleButtonDefaults.ToggleButtonSize,
    iconSize: Dp = PlayToggleButtonDefaults.ToggleButtonIconSize,
    backgroundColor: Color = Color.Transparent,
    checkedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    iconColor: Color = contentColorFor(backgroundColor),
    checkedIconColor: Color = contentColorFor(checkedBackgroundColor)
) {
    val radius = with(LocalDensity.current) { (size / 2).toPx() }
    IconButton(
        onClick = { onCheckedChange(!checked) },
        modifier = modifier
            .size(size)
            .toggleable(value = checked, enabled = enabled, role = Role.Button, onValueChange = {})
            .drawBehind {
                drawCircle(
                    color = if (checked) checkedBackgroundColor else backgroundColor,
                    radius = radius
                )
            },
        enabled = enabled,
        content = {
            Box(
                modifier = Modifier.sizeIn(
                    maxWidth = iconSize,
                    maxHeight = iconSize
                )
            ) {
                val contentColor = if (checked) checkedIconColor else iconColor
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    if (checked) checkedIcon() else icon()
                }
            }
        }
    )
}

/**
 * Now in Android toggle button default values.
 */
object PlayToggleButtonDefaults {
    val ToggleButtonSize = 40.dp
    val ToggleButtonIconSize = 18.dp
}
