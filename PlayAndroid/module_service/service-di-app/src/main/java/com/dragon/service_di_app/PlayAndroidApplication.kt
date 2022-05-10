package com.dragon.service_di_app

import android.content.Context

import com.dragon.module_base.base.BaseApplication
import com.dragon.service_di_app.di.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
open class PlayAndroidApplication : BaseApplication(){
    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        //初始化
        initializers.init(this)
    }
}