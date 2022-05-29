
package com.dragon.service_di_app.init

import android.app.Application
import com.dragon.module_base.service.appinitializers.AppInitializer
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
