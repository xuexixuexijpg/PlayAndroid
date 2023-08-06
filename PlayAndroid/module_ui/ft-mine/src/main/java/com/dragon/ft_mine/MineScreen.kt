package com.dragon.ft_mine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.dragon.ui.mine.R

@Composable
fun MineRoute(
    modifier: Modifier = Modifier,
){
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = stringResource(id = R.string.mine),
            style = TextStyle(fontSize = 22.sp)
        )
    }
}