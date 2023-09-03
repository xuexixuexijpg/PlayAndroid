package com.dragon.data.repository.search

import android.util.Log
import com.dragon.common_base.network.Dispatcher
import com.dragon.common_base.network.PlayDispatchers
import com.dragon.common_base.result.BaseRepository
import com.dragon.data.model.HotWord
import com.dragon.data.model.WanAndroidResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private interface RetrofitSearchApi{

    @GET(value = "hotkey/json")
    suspend fun getHotKey(): WanAndroidResult<List<HotWord>>
}

class ISearchRepository @Inject constructor(
    @Dispatcher(PlayDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    retrofit: Retrofit
) : SearchRepository, BaseRepository() {

    private val searchApi = retrofit.create(RetrofitSearchApi::class.java)

    override fun getHotKeys(): Flow<String> = request {
        //Net里面其实是对okhttp做的封装
       val hotWord =  searchApi.getHotKey()
        if (hotWord.data.isNotEmpty()){
            val i = (hotWord.data.indices).random()
            emit(hotWord.data[i].name)
        }else{
            emit("")
        }
    }.flowOn(ioDispatcher).catch { e ->
        Log.e("错误", "getHotKeys: ${e.message}")
        emit("error")
    }

    override fun getSearchData(key:String): Flow<List<String>>  = request {
        if (key.isEmpty()){
            emit(emptyList())
            return@request
        }
        //b站搜索关键字建议
//        val keys = Net.get(path = "http://api.bilibili.cn/suggest", block = {
//            param("term",key)
//        }).toResult<Map<String, Word>>().getOrNull()
//        if (keys != null){
//            emit(mutableListOf<String>().apply {
//                keys.forEach {
//                    add(it.value.value)
//                }
//            })
//        }else{
            emit(emptyList())
//        }
    }
}