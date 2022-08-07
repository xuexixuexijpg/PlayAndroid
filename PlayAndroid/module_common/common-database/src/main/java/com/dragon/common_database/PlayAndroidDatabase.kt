package com.dragon.common_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dragon.common_database.dao.LocalReadHistoryDao
import com.dragon.common_database.model.LocalReadHistoryEntity
import com.dragon.common_database.util.InstantConverter
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    exportSchema = true,
    entities = [LocalReadHistoryEntity::class],
    version = 1,
    autoMigrations = []
)
@TypeConverters(
    InstantConverter::class,
)
abstract class PlayAndroidDatabase : RoomDatabase() {
    abstract fun localHistoryDao(): LocalReadHistoryDao
}