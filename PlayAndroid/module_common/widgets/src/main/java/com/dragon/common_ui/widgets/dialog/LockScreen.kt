package com.dragon.common_ui.widgets.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlin.math.absoluteValue

const val lockScreenRoute = "module_base_lock_screen"

/**
 * 锁屏页 TODO 如何去除dialog的背景
 * @author lds
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockDialogView(
    modifier: Modifier = Modifier,
    limit: Float = 120F,
    dismiss: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    val showDialog = remember { mutableStateOf(true) }
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { }, properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            )
        ) {
            /*调用原实现*/
//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = {
//                val pagerLayout = PagerLayout(it)
//                pagerLayout.setHideListener {
//                    dismiss()
//                }
//                pagerLayout
//            })
//            val maxWidth = LocalConfiguration.current.screenWidthDp
//            val maxHeight = LocalConfiguration.current.screenHeightDp
            val draEnd = remember { mutableStateOf(false) }
            // 偏移
            val offset = remember { mutableFloatStateOf(0f) }
            // 更新距离
            val scrollState = rememberScrollableState { delta ->
                // 消费滑动的距离，并返回消费的值
                offset.floatValue += delta
                offset.floatValue
            }
//            val flingBehavior = ScrollableDefaults.flingBehavior()
            LaunchedEffect(draEnd.value) {
                if (draEnd.value) {
                    if (offset.floatValue <= 0 && offset.floatValue.absoluteValue > limit) {
                        showDialog.value = false
                        dismiss()
                    } else {
                        offset.floatValue = 0F
                        scrollState.animateScrollBy(0F)
                    }
                }
            }
            val curView = LocalView.current
            /* 去掉dialog里边的颜色 */
            LaunchedEffect(curView) {
                tailrec fun Context.findWindow(): Window? = when (this) {
                    is Activity -> window
                    is ContextWrapper -> baseContext.findWindow()
                    else -> null
                }

                fun View.findWindow(): Window? =
                    (parent as? DialogWindowProvider)?.window ?: context.findWindow()

                try {
                    val window = curView.findWindow() ?: return@LaunchedEffect
                    window.setDimAmount(0F)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            Box(modifier = modifier
//                    .fillMaxSize()
                .requiredSize(1366.dp, 768.dp)
                .offset(y = offset.floatValue.dp)
                .pointerInput(Unit) {
                    detectDragGestures(onDragStart = {
                        draEnd.value = false
                    }, onDragEnd = {
                        draEnd.value = true
                    }) { change, dragAmount ->
                        change.consume()
                        // 获取拖动距离
                        val (x, y) = dragAmount
                        if (y < 0 && !draEnd.value) scrollState.dispatchRawDelta(y)
                        else if (y > 0) {
                            if (offset.floatValue < 0 && !draEnd.value) {
                                draEnd.value = true
                                scrollState.dispatchRawDelta(y)
                            }
                        }
                    }
                }
                .background(Color(0, 0, 0, 102)), contentAlignment = Alignment.Center) {
                if (content == null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        AsyncImage(
//                            model = R.drawable.ic_lock_screen,
//                            contentDescription = null,
//                            contentScale = ContentScale.None
//                        )
//                        LangText(
//                            id = R.string.base_lock_screen, fontSize = 22.sp, color = Color.White
//                        )
                    }
                } else {
                    content.invoke()
                }
            }
        }
    }
}

@Composable
fun LockAndroidView() {

//    LocalContext.current.getActivity<AppCompatActivity>()?.supportFragmentManager?.let {
//
//    }
}

