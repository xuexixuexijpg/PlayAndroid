package com.dragon.search.di

import com.dragon.search.init.SearchInitImpl
import com.dragon.module_base.service.appinitializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
abstract class SearchAppInitializer {
    @Binds
    @IntoSet
    abstract fun provideSearchInit(bind: SearchInitImpl): AppInitializer
}