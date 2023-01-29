package com.dargon.playandroid.init

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.dargon.playandroid.SplashAdActivity

/**
 * 一个空的activity finish并指向SplashActivity
 */
class EmptyActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //跳到
        startActivity(Intent(this@EmptyActivity, SplashAdActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}