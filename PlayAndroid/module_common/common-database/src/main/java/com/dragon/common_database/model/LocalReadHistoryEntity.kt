package com.dragon.common_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant


/**
 * 本地浏览记录
 */
@Entity(tableName = "local_history")
data class LocalReadHistoryEntity(
    @PrimaryKey
    val id:Long,
    val link: String,
    val time: Instant,
    @ColumnInfo(defaultValue = "")
    val author: String
)
