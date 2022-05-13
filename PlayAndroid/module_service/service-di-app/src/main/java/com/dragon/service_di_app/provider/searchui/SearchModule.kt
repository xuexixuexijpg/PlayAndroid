package com.dragon.service_di_app.provider.searchui

import com.dragon.search.SearchProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class SearchModule {
    //注入接口实例
    @Binds
    abstract fun provideSearchContent(searchProvider: SearchProviderImpl): SearchProvider
}