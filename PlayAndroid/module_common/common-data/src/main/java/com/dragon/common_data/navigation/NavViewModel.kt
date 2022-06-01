package com.dragon.common_data.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class NavViewModel : ViewModel(){

    //在home页面的导航选择
    val selectItemNav = MutableSharedFlow<Int>()


}