package com.dragon.common_base.init

import android.app.Application

//初始化接口,提供app的初始化服务

// https://blog.51cto.com/u_15501625/5014426
//关于初始化也可使用jetpack setup来初始化
//更多的sdk加载顺序可参考
//博客 https://www.jianshu.com/p/5f7c100cc651
//模块化启动框架 https://github.com/Leifzhang/AndroidStartup
interface AppInitializer {
    fun init(application: Application)
}