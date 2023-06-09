package com.dragon.common_ui.widgets.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dragon.common.ui.R as commonR
import com.dragon.common_ui.Layout.bodyMargin
import com.dragon.common_ui.Layout.columns
import com.dragon.common_ui.Layout.gutter
import com.dragon.common_ui.bodyWidth
import com.dragon.common_ui.plus
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Search(
    modifier: Modifier = Modifier,
    openDetails: (String) -> Unit, //点击一条
    onSearchQueryChanged: (query: String) -> Unit, //搜索输入变化
    stateFlow: StateFlow<SearchViewState>,
    focus: Boolean = true,
    onClickSearchBar: () -> Unit = {}
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
    Scaffold(
        modifier = modifier.background(color = Color.Transparent),
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0f),  // 设置色值
        topBar = {
            Box(
                Modifier
                    .statusBarsPadding()
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {
                var searchQuery by remember { mutableStateOf(TextFieldValue(state.query)) }
                var hintStr = state.hintStr
                if (hintStr.isEmpty()) {
                    hintStr = stringResource(commonR.string.hint_search)
                }
                if (textFiledFocus) {
                    SearchTextField(
                        value = searchQuery,
                        onValueChange = { value ->
                            searchQuery = value
                            onSearchQueryChanged(value.text)
                        },
                        hint = hintStr,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(60)
                            )
                            .fillMaxHeight()
                            .width(200.dp)
                            .clickable {
                                onClickSearchBar()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = commonR.drawable.ic_baseline_search_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp, 18.dp)
                                .padding(start = 5.dp)
                        )
                        Text(
                            text = hintStr,
                            modifier = Modifier.padding(start = 5.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    ) { padding ->
        if (!textFiledFocus) return@Scaffold
        SearchList(
            contentPadding = PaddingValues(horizontal = bodyMargin) + padding,
            results = state.searchResults,
            onItemClick = { openDetails(it) },
            modifier = Modifier.bodyWidth()
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
        items(items = results,
            key = { it } //TODO 搜索词应该没重复的直接用做key
        ) {
            SearchRow(
                show = it,
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .clickable { onItemClick(it) }
            )
        }
    }
}

@Composable
private fun SearchRow(
    show: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = show,
            style = MaterialTheme.typography.displayMedium
        )
    }
}   