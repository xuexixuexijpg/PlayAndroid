package com.dragon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dragon.database.dao.ImageDao
import com.dragon.database.model.ImagesEntity
import com.dragon.database.utils.DrawableConverter

@Database(entities = [ImagesEntity::class], version = 1, exportSchema = true)
@TypeConverters(DrawableConverter::class)
abstract class PlayDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}