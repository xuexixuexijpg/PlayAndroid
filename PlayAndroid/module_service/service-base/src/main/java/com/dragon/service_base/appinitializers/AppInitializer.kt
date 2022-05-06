
package com.dragon.service_base.appinitializers

import android.app.Application

//初始化接口,提供app的初始化服务
interface AppInitializer {
    fun init(application: Application)
}
