package com.dragon.common_base.init

import android.app.Application

//初始化接口,提供app的初始化服务

// https://blog.51cto.com/u_15501625/5014426
//关于初始化也可使用jetpack setup来初始化
//更多的sdk加载顺序可参考
//博客 https://www.jianshu.com/p/5f7c100cc651
//模块化启动框架 https://github.com/Leifzhang/AndroidStartup

//一些优化相关
//https://juejin.cn/post/7138596300672958471#heading-20
interface AppInitializer {
    //一些基础的放主线程的application中加载
    fun init(application: Application)
}