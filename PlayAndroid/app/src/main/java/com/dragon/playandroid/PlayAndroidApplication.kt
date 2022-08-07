package com.dragon.playandroid

import com.dragon.module_base.base.BaseApplication
import com.dragon.playandroid.init.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * hilt升级到2.43之后只能放到app module模块
 * https://github.com/google/dagger/issues/3400
 */
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