package com.dragon.common_database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesLocalHistoryDao(
        database: PlayAndroidDatabase
    ) = database.localHistoryDao()
}