package com.dragon.data.repository.search

import android.util.Log
import com.dragon.common_base.network.Dispatcher
import com.dragon.common_base.network.PlayDispatchers
import com.dragon.data.model.HotWord
import com.dragon.data.model.Word
import com.dragon.common_base.result.BaseRepository
import com.drake.net.Net
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ISearchRepository @Inject constructor(
    @Dispatcher(PlayDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository, BaseRepository() {
    override fun getHotKeys(): Flow<String> = request {
        //Net里面其实是对okhttp做的封装
       val hotWord =  Net.get("hotkey/json").toResult<List<HotWord>>().getOrNull()
        if (!hotWord.isNullOrEmpty()){
            val i = (hotWord.indices).random()
            emit(hotWord[i].name)
        }else{
            emit("")
        }
    }.flowOn(ioDispatcher).catch { e ->
        Log.e("错误", "getHotKeys: ${e.message}")
    }

    override fun getSearchData(key:String): Flow<List<String>>  = request {
        if (key.isEmpty()){
            emit(emptyList())
            return@request
        }
        //b站搜索关键字建议
        val keys = Net.get(path = "http://api.bilibili.cn/suggest", block = {
            param("term",key)
        }).toResult<Map<String, Word>>().getOrNull()
        if (keys != null){
            emit(mutableListOf<String>().apply {
                keys.forEach {
                    add(it.value.value)
                }
            })
        }else{
            emit(emptyList())
        }
    }
}