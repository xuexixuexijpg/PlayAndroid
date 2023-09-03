package com.dragon.common_ui.widgets.search

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dragon.common.ui.R
import com.dragon.common_ui.Layout.columns
import com.dragon.common_ui.Layout.gutter
import com.dragon.common_ui.widgets.langeview.getLangString
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Search(
    modifier: Modifier = Modifier, openDetails: (String) -> Unit, //点击一条
    onSearchQueryChanged: (query: String) -> Unit, //搜索输入变化
    stateFlow: StateFlow<SearchViewState>, focus: Boolean = true, onClickSearchBar: () -> Unit = {}
) {
    val viewState by stateFlow.collectAsStateWithLifecycle()
    Search(
        modifier = modifier,
        textFiledFocus = focus,
        state = viewState,
        openDetails = openDetails,
        onSearchQueryChanged = onSearchQueryChanged,
        onClickSearchBar = onClickSearchBar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Search(
    modifier: Modifier = Modifier,
    state: SearchViewState,
    openDetails: (String) -> Unit,
    onSearchQueryChanged: (query: String) -> Unit,
    textFiledFocus: Boolean = false,
    onClickSearchBar: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var hintStr = state.hintStr
    if (hintStr.isEmpty()) {
        hintStr = getLangString(R.string.hint_search)
    }
    val listener = { s: String ->
        searchQuery = s
    }
    Row(
        modifier
            .clickable( // 去除点击效果
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                if (!textFiledFocus)
                    onClickSearchBar()
            }
            .statusBarsPadding()
            .fillMaxHeight()
            .wrapContentWidth()
    ) {
        CustomTextField(
            value = searchQuery, onValueChange = listener,
            modifier = Modifier.height(30.dp),
            trailingIcon = {
                androidx.compose.animation.AnimatedVisibility(
                    visible = searchQuery.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = { listener("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.cd_clear_text)
                        )
                    }
                }
            },
            placeholder = { Text(text = hintStr, color = Color.Gray) },
            enabled = textFiledFocus
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchList(
    results: List<String>,
    onItemClick: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier,
) {
    val arrangement = Arrangement.spacedBy(gutter)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns / 4),
        contentPadding = contentPadding,
        verticalArrangement = arrangement,
        horizontalArrangement = arrangement,
        modifier = modifier
    ) {
        items(items = results, key = { it } //TODO 搜索词应该没重复的直接用做key
        ) {
            SearchRow(show = it,
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .clickable { onItemClick(it) })
        }
    }
}

@Composable
private fun SearchRow(
    show: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = show, style = MaterialTheme.typography.displayMedium
        )
    }
}
