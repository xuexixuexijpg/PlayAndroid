package com.dragon.ft_main_home.entity

import kotlinx.serialization.Serializable

/**
 * Author:Knight
 * Time:2022/3/11 16:28
 * Description:OfficialAccountEntity
 */
@Serializable
data class OfficialAccountEntity(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
