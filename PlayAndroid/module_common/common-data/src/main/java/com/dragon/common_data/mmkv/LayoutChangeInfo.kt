package com.dragon.common_data.mmkv

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 决定MainFragment / 其他页面跟随界面时的变化
 * 直接根据屏幕宽度来 先不适配折叠屏
 *
 * 分界岭 width600 宽度默认0小于600dp 1024>1>600 >1024 2
 */
@Parcelize
data class LayoutChangeInfo(
    val screenWidth : Int = 0
) : Parcelable