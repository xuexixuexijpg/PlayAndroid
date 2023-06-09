package com.dragon.common_network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 *
 * 配合缓存拦截器
 *
 */
class CacheHelperInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        // 缓存 10 秒
        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.SECONDS)
            .build()

        return response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}