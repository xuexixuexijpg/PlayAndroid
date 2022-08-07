package com.dragon.common_database

import android.content.Context
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesPlayAndroidDB(
        @ApplicationContext context: Context
    ): PlayAndroidDatabase {
        val pass = context.run {
            "&dragon" + getString(R.string.p1) + getString(R.string.p2)
        }
        return Room.databaseBuilder(
            context,
            PlayAndroidDatabase::class.java,
            "pa-database"
        )
//            .openHelperFactory(SupportFactory(pass.toByteArray()))
            .build()
    }

}