package com.dragon.common_base.init

import android.app.Application

//初始化接口,提供app的初始化服务

// https://blog.51cto.com/u_15501625/5014426
//关于初始化也可使用jetpack setup来初始化
interface AppInitializer {
    fun init(application: Application)
}