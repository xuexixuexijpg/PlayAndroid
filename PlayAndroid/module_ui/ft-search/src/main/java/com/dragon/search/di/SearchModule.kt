package com.dragon.search.di

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