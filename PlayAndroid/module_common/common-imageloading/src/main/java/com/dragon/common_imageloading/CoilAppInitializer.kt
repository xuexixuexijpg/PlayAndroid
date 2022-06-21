
package com.dragon.common_imageloading

import android.app.Application
import android.util.Log

import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.dragon.module_base.service.appinitializers.AppInitializer
import okhttp3.OkHttpClient
import javax.inject.Inject

class CoilAppInitializer @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : AppInitializer {
    override fun init(application: Application) {
        //TODO 能不能跟网络请求使用同一个 client ??
        //NetConfig.okHttpClient
        val coilOkHttpClient = okHttpClient.newBuilder()
            .build()
        Coil.setImageLoader {
            ImageLoader.Builder(application)
                .components {
//                    add(tmdbImageEntityInterceptor)
//                    add(episodeEntityInterceptor)
                    //可增加拦截器
                    //可增加其他component模块，如gif,svg,video模块(需引入相关库)
                }
                .memoryCache {
                    //内存
                    MemoryCache.Builder(application)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCache {
                    //磁盘
                    DiskCache.Builder()
                        .directory(application.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .okHttpClient(coilOkHttpClient)
                .build()
        }
        Log.e("测试", "init: coil == ${Coil.imageLoader(application)} == $okHttpClient", )
    }
}
