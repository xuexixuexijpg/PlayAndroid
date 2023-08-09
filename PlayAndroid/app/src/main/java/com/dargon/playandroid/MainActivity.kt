package com.dargon.playandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.dargon.playandroid.init.ForegroundCheck
import com.dargon.playandroid.langtest.Test
import com.dargon.playandroid.ui.PlayAndroidApp
import com.dragon.common_network.utils.NetworkMonitor
import com.dragon.common_utils.gesture.MultiGestureMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private var isBack: Boolean = false
    private var gesture: MultiGestureMonitor? = null

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() //android12

        super.onCreate(savedInstanceState)
        Log.e("测试", "onCreate: Main 别名 ${intent.component?.className}")

        splashScreen.setKeepOnScreenCondition {
            false
        }//是否要一直在splash界面条件

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()

//            PlayAndroidApp(calculateWindowSizeClass(this), networkMonitor)
            Test()
        }
        gesture = MultiGestureMonitor(this)
    }

    override fun onResume() {
        super.onResume()
        if (isBack) {
            isBack = false
            startActivity(Intent(this, SplashAdActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("测试", "onPause: Main 别名 ${intent.component?.className}")
        if (!ForegroundCheck.get().isForeground) {
            isBack = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("测试", "onDestroy: Main")
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        gesture?.let {
            if (it.onTouchEvent(ev)) return true
        }
        return super.dispatchTouchEvent(ev)
    }

}
