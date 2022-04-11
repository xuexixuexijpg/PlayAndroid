package com.dragon.module_base.base.viewmodel

import androidx.lifecycle.ViewModel
import com.dragon.module_base.support.event.EventLiveData
/*
* 基础viewModel
* */
class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange {
        //显示隐藏加载框
        val showDialog by lazy { EventLiveData<String>() }
        val dismissDialog by lazy { EventLiveData<Boolean>()}
    }

}