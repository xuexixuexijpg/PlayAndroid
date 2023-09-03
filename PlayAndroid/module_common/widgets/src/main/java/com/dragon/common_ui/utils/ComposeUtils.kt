package com.dragon.common_ui.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.core.app.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 从dimens中获取来转sp
 */
@Composable
@ReadOnlyComposable
fun fontSizeFromDimens(@DimenRes id: Int): TextUnit {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val value = ctx.resources.getDimension(id)
    return TextUnit(value / density.density, TextUnitType.Sp)
}

/**
 * 获取Activity
 */
inline fun <reified Activity : ComponentActivity> Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            while (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}

/**
 * 为Composable函数生命周期处理 LaunchedEffect不接收 ON_CREATE和ON_START事件
 */
@Composable
fun DisposableEffectWithLifecycle(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onStop: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onDestroy: () -> Unit = {},
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentOnCreate by rememberUpdatedState(onCreate)
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnDestroy by rememberUpdatedState(onDestroy)

    DisposableEffect(key1 = lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> currentOnCreate()
                Lifecycle.Event.ON_START -> currentOnStart()
                Lifecycle.Event.ON_STOP -> currentOnStop()
                Lifecycle.Event.ON_RESUME -> currentOnResume()
                Lifecycle.Event.ON_PAUSE -> currentOnPause()
                Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}

/**
 * 对vm进行扩展增加生命周期监听
 */
@Composable
fun <viewModel : LifecycleObserver> viewModel.observeLifecycleEvents(lifecycle: Lifecycle){
    DisposableEffect(lifecycle){
        lifecycle.addObserver(this@observeLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycleEvents)
        }
    }
}