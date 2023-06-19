package com.dragon.datastore.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.dragon.datastore.CryptoManager
import com.dragon.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer,
    ) {
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
            migrations = listOf(
                //合并操作 升级操作
            ),
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
    }

    @Provides
    @Singleton
    fun providesCryManager() = CryptoManager()
}