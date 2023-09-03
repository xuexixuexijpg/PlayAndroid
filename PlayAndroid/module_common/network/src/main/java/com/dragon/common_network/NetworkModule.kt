package com.dragon.common_network

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dragon.common.network.BuildConfig
import com.dragon.common_network.BaseConfig.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
        coerceInputValues = true // 如果JSON字段是Null则使用默认值
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        //如需在其他模块添加全局拦截器 可使用hilt注入到set中
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ):  Call.Factory = OkHttpClient.Builder()
        .apply {
            //拦截器
            interceptors.forEach(::addInterceptor)
            // 通知栏监听网络日志
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .collector(ChuckerCollector(context))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
            }
        }
        // 缓存 100M
        .cache(Cache(File(context.cacheDir, "api_cache"), 100L * 1024 * 1024))
        // Adjust the Connection pool to account for historical use of 3 separate clients
        // but reduce the keepAlive to 2 minutes to avoid keeping radio open.
        .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
        .dispatcher(
            Dispatcher().apply {
                // Allow for increased number of concurrent image fetches on same host
                maxRequestsPerHost = 10
            }
        )
        // Increase timeouts
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
        // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
        // AndroidStudio OkHttp Profiler 插件输出网络日志
        // 添加持久化Cookie管理
//        .cookieJar(PersistentCookieJar(context))
        // 添加请求拦截器, 可配置全局/动态参数
//            setRequestInterceptor(MyRequestInterceptor())
        // 数据转换器


//    @Singleton
//    @Provides
//    fun initNetWork(
//        @ApplicationContext context: Context,
//        client: OkHttpClient.Builder
//    ): OkHttpClient {
//        NetConfig.initialize(BaseConfig.BASE_URL, context, client)
//        return NetConfig.okHttpClient
//    }

    @Singleton
    @Provides
    fun provideRetrofit(
        networkJson: Json,
        okhttpCallFactory:  Call.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()

    /**
     * Since we're displaying SVGs in the app, Coil needs an ImageLoader which supports this
     * format. During Coil's initialization it will call `applicationContext.newImageLoader()` to
     * obtain an ImageLoader.
     *
     * @see <a href="https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt">Coil</a>
     */
    @Provides
    @Singleton
    fun imageLoader(
        okhttpCallFactory: Call.Factory,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .callFactory(okhttpCallFactory)
        .components {
//            add(SvgDecoder.Factory())
        }
        // Assume most content images are versioned urls
        // but some problematic images are fetching each time
        .respectCacheHeaders(false)
        .apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
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
        .build()


}