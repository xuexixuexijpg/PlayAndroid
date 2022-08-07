package com.dragon.common_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dragon.common_database.model.LocalReadHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalReadHistoryDao {

    @Query("select * from local_history l order by l.time")
    fun getAllReadHistory():Flow<List<LocalReadHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrIgnoreHistory(historyEntities: List<LocalReadHistoryEntity>): List<Long>
}