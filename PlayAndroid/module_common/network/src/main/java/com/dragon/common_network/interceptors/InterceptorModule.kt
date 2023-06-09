package com.dragon.common_network.interceptors

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InterceptorModule {

    @Provides
    @IntoSet
    @Singleton
    fun provideCacheInterceptor(@ApplicationContext context: Context):Interceptor{
        return CacheInterceptor(context)
    }


    @Provides
    @IntoSet
    @Singleton
    fun provideCacheHelperInterceptor():Interceptor{
        return CacheHelperInterceptor()
    }

}