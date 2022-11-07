package com.dragon.database.model

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_store")
data class ImagesEntity(
    @PrimaryKey
    val id : Long,
    @ColumnInfo
    val imageData:Drawable,
    val pathStr : String? //uri http 之类的
)
