package com.dragon.ft_main_home

import com.dragon.module_base.service.navigate.BaseArgs
import com.dragon.module_base.service.navigate.Navigator
import kotlinx.parcelize.Parcelize

interface HomeProvider: Navigator<HomeArgs> {
}

@Parcelize
class HomeArgs(
    val page: String
) : BaseArgs()