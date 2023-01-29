package com.dargon.playandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dragon.common_designsystem.component.PlayBackground
import com.dragon.common_designsystem.theme.PlayTheme
import com.dragon.playandroid.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 广告启动页 不需要
 */
@SuppressLint("CustomSplashScreen")
@Deprecated("不需要，项目无需")
class SplashAdActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            Text(text = "闪屏页")
            SplashScreen()
        }

        splashScreen.setKeepOnScreenCondition {
            false
        }

        //必须process版本为2.4.0版本以上 (集成process默认添加startup库)
//        ProcessLifecycleOwner.get().lifecycle.addObserver(AutoForegroundObserver())

        //根据版本判断是否使用此方法。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreenCloseAnimation()
        } else {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    delay(2000)
//                    startActivity(Intent().apply {
//                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        component = ComponentName(this@SplashAdActivity,MainActivity::class.java)
//                    })
//                    this@SplashAdActivity.finish()
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun splashScreenCloseAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
            startActivity(Intent().apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            this@SplashAdActivity.finish()
        }
    }

}

@Composable
fun SplashScreen() {
    PlayTheme {
        PlayBackground {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.ic_splash_bg).crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}