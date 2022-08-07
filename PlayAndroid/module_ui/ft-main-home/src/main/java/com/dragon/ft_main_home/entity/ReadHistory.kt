package com.dragon.ft_main_home.entity

import com.dragon.common_database.model.LocalReadHistoryEntity
import com.dylanc.longan.format
import kotlinx.datetime.Instant
import java.time.format.DateTimeFormatter


data class ReadHistory(
    val id:Long,
    val link: String,
    val time: String,
    val author: String
)

/**
 * 将LocalReadHistoryEntity转为本地需求的model
 */
fun LocalReadHistoryEntity.asExternalModel() = ReadHistory(
    id = id,
    link = link,
    time = time.format("yyyy-MM-dd HH:mm:ss"),
    author = author
)