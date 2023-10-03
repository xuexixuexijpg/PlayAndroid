package com.dragon.ft_search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dragon.common_ui.widgets.search.CustomTextField


@Composable
fun SearchRoute() {
    var searchQuery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(LocalTextInputService provides null) {

            CustomKeyboardDemo()
        }
    }
}

@Composable
fun CustomKeyboardDemo() {
    // 定义一个可变状态作为TextField的值
    var text by remember { mutableStateOf(TextFieldValue("22232d")) }
    // 定义一个焦点请求器
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 创建一个TextField
        CustomTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        text = text.copy(selection = TextRange(0, text.text.length))
                    }
                },
        )
    }
    DisposableEffect(key1 = Unit, effect = {
        text = TextFieldValue("43443d")
        focusRequester.requestFocus()
        onDispose {  }
    })
}

