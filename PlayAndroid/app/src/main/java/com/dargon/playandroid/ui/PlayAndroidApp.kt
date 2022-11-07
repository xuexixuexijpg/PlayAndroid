package com.dargon.playandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.dargon.playandroid.navigation.NiaNavHost
import com.dragon.common_designsystem.component.PlayBackground
import com.dragon.common_designsystem.theme.PlayTheme

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun  PlayAndroidApp(
    windowSizeClass: WindowSizeClass,
    appState: PlayAppState = rememberPlayAppState(windowSizeClass)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    //设置主题
    PlayTheme {
        PlayBackground {
            //脚手架
            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                }.fillMaxHeight().fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) { padding ->
                Row(
                    //https://google.github.io/accompanist/insets/
                    Modifier
                        .fillMaxWidth().fillMaxHeight()
                ) {
                    NiaNavHost(
                        navController = appState.navController,
                        onBackClick = appState::onBackClick,
                        appState = appState,
                        modifier = Modifier
                            .padding(padding)
                            .consumedWindowInsets(padding),
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    }
}


