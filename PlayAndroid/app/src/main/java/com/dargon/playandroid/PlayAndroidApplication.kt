package com.dargon.playandroid


//import com.dargon.playandroid.AppInitializers
import android.annotation.SuppressLint
import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.dargon.playandroid.init.ForegroundCheck
import com.dargon.playandroid.init.HookManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
open class PlayAndroidApplication : Application(),ImageLoaderFactory {
//    @Inject
//    lateinit var initializers: AppInitializers

    @Inject
    lateinit var imageLoader:Provider<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        //初始化
//        hookActivityThread()
//        initializers.init(this)
        ForegroundCheck.init(this)
    }

    /**
     * 解决外部启动多实例问题
     */
    @SuppressLint("PrivateApi")
    private fun hookActivityThread() {
        //根据版本来替换不同的
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            AppComponentFactory().instantiateActivity()
//        }else{
//
//        }
        try {
            HookManager.init()
            HookManager.injectInstrumentation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}