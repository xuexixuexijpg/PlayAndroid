package com.dragon.ft_home

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dragon.common_ui.widgets.search.Search
import com.dragon.common_ui.widgets.search.SearchViewState
import com.dragon.ui.home.R
import kotlinx.coroutines.flow.StateFlow

/**
 * 首页
 */
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSetting: () -> Unit,
) {
    Column(
        modifier = Modifier
    ) {
        HeaderSearch(
            navigateToSearch,
            navigateToHome,
            searchState = viewModel.searchState,
            onSearchQueryChanged = viewModel::search
        )
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(15) { index ->
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .border(1.dp, color = Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "index $index",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSearch(
    navigateToSearch: () -> Unit,
    navigateToHome: () -> Unit,
    searchState: StateFlow<SearchViewState>,
    onSearchQueryChanged: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        //头像
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F22e099fb2ed31587aac6aeea565e1be9c5fde254.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665752286&t=7d2096522fe7c0c4a480fc3c2d3e21ba")
                .allowConversionToBitmap(true)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp, 30.dp)
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .clickable {
                    navigateToHome()
                },
        )
        //搜索条
        Search(
            modifier = Modifier
                .wrapContentWidth(),
            openDetails = {},
            onSearchQueryChanged = { onSearchQueryChanged(it) },
            stateFlow = searchState,
            focus = false,
            onClickSearchBar = { navigateToSearch() }
        )
        Row(modifier = Modifier.padding(end = 10.dp).align(Alignment.CenterEnd)) {
            //一些图片
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.ic_baseline_videogame).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        Toast
                            .makeText(context, "游戏点击", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .fillMaxHeight()
            )
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.ic_baseline_email_24).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .fillMaxHeight()
            )
        }

    }
}


