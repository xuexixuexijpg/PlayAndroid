package com.dragon.data.di

import com.dragon.data.repository.search.ISearchRepository
import com.dragon.data.repository.search.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsSearchRepository(
        searchRepository: ISearchRepository
    ):SearchRepository

}