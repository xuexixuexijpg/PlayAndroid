package com.dragon.ft_main_mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() :ViewModel() {

    val testData = MutableStateFlow<MutableList<MineData>?>(null)

    fun setData(count:Int){
        viewModelScope.launch {
            delay(2000)
            val data = mutableListOf<MineData>().apply {
                if (count == 100){
                    for (i in 1..count) {
                        add(MineData(i.toString()))
                    }
                }else {
                    for (i in 1..count) {
//                        add(MineData(i.toString()+"test"))
                        add(MineData(i.toString()))
                    }
                }
            }
            testData.emit(data)
        }
    }
}