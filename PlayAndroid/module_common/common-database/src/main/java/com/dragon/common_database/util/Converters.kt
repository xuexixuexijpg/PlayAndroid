package com.dragon.common_database.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.Instant.Companion.fromEpochMilliseconds

class InstantConverter{
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}