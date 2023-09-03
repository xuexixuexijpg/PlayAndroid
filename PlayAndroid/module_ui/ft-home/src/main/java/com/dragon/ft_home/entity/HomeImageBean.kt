package com.dragon.ft_home.entity

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable

/**
 * 左侧图片
 */
@Stable
data class HomeImageBean(
    //本地图片
    @DrawableRes val imageModel : Int = -1,
    //活动图片 用于网络加载
    val activeImageModel : String = "",
    //唯一
    val key : Int
)
