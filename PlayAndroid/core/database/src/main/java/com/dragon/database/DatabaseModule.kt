package com.dragon.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //单例注入
    @Provides
    @Singleton
    fun providesPlayDatabase(
        @ApplicationContext context: Context
    ): PlayDatabase {
        val pass = context.run {
            "&dragon" + getString(R.string.p1) + getString(R.string.p2)
        }
        return Room.databaseBuilder(
            context,
            PlayDatabase::class.java,
            "play-database"
        ).openHelperFactory(SupportFactory(pass.toByteArray()))
            .build()
    }
}