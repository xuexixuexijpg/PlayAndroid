
package com.dragon.common_designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PlayTopicTag(
    followed: Boolean,
    onFollowClick: () -> Unit,
    onUnfollowClick: () -> Unit,
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    followText: @Composable () -> Unit,
    unFollowText: @Composable () -> Unit,
    browseText: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        val containerColor = if (followed) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
        PlayTextButton(
            onClick = { expanded = true },
            enabled = enabled,
            small = true,
            colors = PlayButtonDefaults.textButtonColors(
                containerColor = containerColor,
                contentColor = contentColorFor(backgroundColor = containerColor),
                disabledContainerColor = if (followed) {
                    MaterialTheme.colorScheme.onBackground.copy(
                        alpha = PlayButtonDefaults.DisabledButtonContentAlpha
                    )
                } else {
                    Color.Transparent
                }
            ),
            text = text
        )
        NiaDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            items = if (followed) listOf(UNFOLLOW, BROWSE) else listOf(FOLLOW, BROWSE),
            onItemClick = { item ->
                when (item) {
                    FOLLOW -> onFollowClick()
                    UNFOLLOW -> onUnfollowClick()
                    BROWSE -> onBrowseClick()
                }
            },
            itemText = { item ->
                when (item) {
                    FOLLOW -> followText()
                    UNFOLLOW -> unFollowText()
                    BROWSE -> browseText()
                }
            }
        )
    }
}

private const val FOLLOW = 1
private const val UNFOLLOW = 2
private const val BROWSE = 3
