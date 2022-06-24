package com.dragon.ft_main_square.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Uninitialized


data class SquareData(val textData:MutableList<String> = mutableListOf()):MavericksState


class SquareViewModel(private val data:SquareData) :MavericksViewModel<SquareData>(data){

    fun setData(list:MutableList<String>) = setState {
        copy(textData = list)
    }

    fun getData() = data
}