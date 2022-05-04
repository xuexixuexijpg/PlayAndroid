package com.dragon.service_di_app

import android.content.Context

import com.dragon.module_base.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlayAndroidApplication : BaseApplication(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
}