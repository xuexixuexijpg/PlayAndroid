package com.dragon.search

import com.dragon.module_base.service.navigate.BaseArgs
import com.dragon.module_base.service.navigate.Navigator
import kotlinx.parcelize.Parcelize

interface SearchProvider : Navigator<SearchArgs> {

}

@Parcelize
class SearchArgs(
    val page: String
) : BaseArgs()