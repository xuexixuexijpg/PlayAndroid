package com.dargon.playandroid


import android.app.Application
//import com.dargon.playandroid.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
open class PlayAndroidApplication : Application(){
    @Inject
//    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        //初始化
//        initializers.init(this)
    }
}