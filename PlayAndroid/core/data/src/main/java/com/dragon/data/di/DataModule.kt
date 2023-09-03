package com.dragon.data.di

import com.dragon.data.repository.search.ISearchRepository
import com.dragon.data.repository.search.SearchRepository
import com.dragon.data.repository.user.OfflineUserDataRepository
import com.dragon.data.repository.user.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsSearchRepository(
        searchRepository: ISearchRepository
    ):SearchRepository

    @Binds
    @Singleton
    fun bindsUserDataRepository(
        userDataRepository: OfflineUserDataRepository
    ):UserDataRepository
}