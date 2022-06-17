package com.dragon.ft_main_mine

import android.util.Log
import com.airbnb.mvrx.*
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

data class MineData(val title: String)

data class MineDataList(
    val resultData: List<MineData>? = emptyList(),
    val data: Async<List<MineData>> = Uninitialized
) : MavericksState

class MineViewModel @AssistedInject constructor(
    @Assisted initData: MineDataList,
    private val mineRepository: MineRepository
) : MavericksViewModel<MineDataList>(initData) {

    init {
        getData()
    }

    fun getData() = withState {
        if (it.data is Loading) return@withState
        suspend {
            delay(2000)
            mutableListOf<MineData>().apply {
                for (i in 1..100) {
                    add(MineData(i.toString()))
                }
            }
        }.execute { result -> copy(data = result, resultData = result.invoke() ?: emptyList()) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MineViewModel, MineDataList> {
        override fun create(state: MineDataList): MineViewModel
    }

    //要使用VM工厂来创建viewModel
    companion object :
        MavericksViewModelFactory<MineViewModel, MineDataList> by hiltMavericksViewModelFactory()
}
