package com.dragon.common_network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dragon.common_network.converter.SerializationConverter
import com.drake.net.BuildConfig
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        //如需在其他模块添加全局拦截器 可使用hilt注入到set中
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient.Builder = OkHttpClient.Builder()
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
        // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
        .setDebug(BuildConfig.DEBUG)
        // AndroidStudio OkHttp Profiler 插件输出网络日志
        .addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))
        // 添加持久化Cookie管理
        .cookieJar(PersistentCookieJar(context))
        // 添加请求拦截器, 可配置全局/动态参数
//            setRequestInterceptor(MyRequestInterceptor())
        // 数据转换器
        .setConverter(SerializationConverter())

    @Singleton
    @Provides
    fun initNetWork(
        @ApplicationContext context: Context,
        client: OkHttpClient.Builder
    ): OkHttpClient {
        NetConfig.initialize(BaseConfig.BASE_URL, context, client)
        return NetConfig.okHttpClient
    }

}