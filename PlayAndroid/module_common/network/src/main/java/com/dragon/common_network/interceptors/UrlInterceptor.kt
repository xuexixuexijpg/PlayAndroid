package com.dragon.common_network.interceptors

import com.dragon.common_network.BaseConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 自定义修改请求头
 */
enum class UrlHost(val host : String){
    //
    BASE_URL("base"),
    BILIBILI_URL("bilibili"),
}

/**
 * Url拦截器
 */
class UrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 获取原始请求对象
        val originalRequest = chain.request();
        // 获取原始请求头中的Host字段值
        val host = originalRequest.header("Host");
        // 根据Host字段值来判断要切换成哪个baseUrl
        var  newBaseUrl = "";
        if (host.equals(UrlHost.BILIBILI_URL.host)) {
            newBaseUrl = "https://cn.api.com/";
        } else {
            newBaseUrl = BaseConfig.BASE_URL
        }
        // 获取原始请求中的相对路径
        val relativePath = originalRequest.url.encodedPath;
        // 拼接新的完整路径
        val newUrl = newBaseUrl + relativePath;
        // 创建新的请求对象，并设置新的url地址
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build();
        // 继续执行请求，并返回响应
        return chain.proceed(newRequest);
    }
}