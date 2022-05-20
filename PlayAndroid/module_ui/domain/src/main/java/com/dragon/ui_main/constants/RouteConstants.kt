package com.dragon.ui_main.constants

import com.dragon.common_utils.ext.FragClazz
import com.dragon.ft_main.MainFragment
import com.dragon.search.SearchFragment
import kotlin.reflect.KClass

/**
 * 路由表
 */
sealed class RouteConstants(val clazz: FragClazz) {
    object Search : RouteConstants(SearchFragment::class)

    object Main : RouteConstants(MainFragment::class)
}