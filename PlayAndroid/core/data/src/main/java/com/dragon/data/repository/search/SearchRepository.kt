package com.dragon.data.repository.search

import kotlinx.coroutines.flow.Flow


interface SearchRepository {
    fun getHotKeys(): Flow<String>

    fun getSearchData(key:String): Flow<List<String>>
}