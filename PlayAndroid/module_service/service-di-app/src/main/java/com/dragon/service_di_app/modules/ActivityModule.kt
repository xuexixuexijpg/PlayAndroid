package com.dragon.service_di_app.modules

import com.dragon.ft_main.MainProvider
import com.dragon.search.SearchProvider
import com.dragon.service_di_app.provider.MainProviderImpl
import com.dragon.service_di_app.provider.SearchProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
abstract class ActivityModule {

    //注入接口实例
    @Binds
    abstract fun provideSearchContent(searchProvider: SearchProviderImpl): SearchProvider

    @Binds
    abstract fun provideMainContent(mainProvider: MainProviderImpl): MainProvider
}