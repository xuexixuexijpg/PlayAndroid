

package com.dragon.common_designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dragon.common_designsystem.component.NiaTextButton
import com.dragon.common_designsystem.icon.PlayIcons


/**
 * Now in Android view toggle button with included trailing icon as well as compact and expanded
 * text label content slots.
 */
@Composable
fun PlayViewToggleButton(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compactText: @Composable () -> Unit,
    expandedText: @Composable () -> Unit
) {
    PlayTextButton(
        onClick = { onExpandedChange(!expanded) },
        modifier = modifier,
        enabled = enabled,
        text = if (expanded) expandedText else compactText,
        trailingIcon = {
            Icon(
                imageVector = if (expanded) PlayIcons.ViewDay else PlayIcons.ShortText,
                contentDescription = null
            )
        }
    )
}
