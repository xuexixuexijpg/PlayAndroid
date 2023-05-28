package com.dargon.playandroid.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dargon.playandroid.navigation.NiaNavHost
import com.dragon.common_designsystem.component.PlayBackground
import com.dragon.common_designsystem.theme.PlayTheme
import com.dragon.common_network.state.NetworkState
import com.dragon.common_network.utils.NetworkMonitor
import com.dragon.playandoird.R

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun  PlayAndroidApp(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    appState: PlayAppState = rememberPlayAppState(windowSizeClass,networkMonitor)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    //设置主题
    PlayTheme {
        PlayBackground {
            Scaffold(
                modifier = Modifier
                    .semantics {
                        testTagsAsResourceId = true
                    }
                    .fillMaxHeight()
                    .fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                snackbarHost = { SnackbarHost(modifier = Modifier
                    .navigationBarsPadding(), hostState = snackbarHostState) },
            ) { padding ->

                //网络状态监听
                val isOffline by appState.isOffline.collectAsStateWithLifecycle()
                val notConnected = stringResource(R.string.not_connected)
                LaunchedEffect(isOffline) {
                    if (isOffline == NetworkState.NONE) snackbarHostState.showSnackbar(
                        message = notConnected,
                        duration = SnackbarDuration.Short
                    )
                }

                Row(
                    //https://google.github.io/accompanist/insets/
                    Modifier.fillMaxSize()
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


