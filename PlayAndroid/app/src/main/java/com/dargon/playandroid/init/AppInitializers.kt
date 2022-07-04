package com.dargon.playandroid.init

import android.app.Application
import com.dragon.common_base.init.AppInitializer
import javax.inject.Inject

//进行所有的注入模块初始化
class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}