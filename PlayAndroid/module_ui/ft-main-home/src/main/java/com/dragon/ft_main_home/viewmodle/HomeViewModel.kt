package com.dragon.ft_main_home.viewmodle

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

/**
 * private public internal protected 修饰符
 */

/**
 * 数据模型
 */
data class HomeViewState(
    val hintText:String ="sdkah"
):MavericksState

/**
 * viewModel internal修饰只对当前模块提供调用
 */
internal class HomeViewModel(initialState: HomeViewState) : MavericksViewModel<HomeViewState>(initialState) {
    //改变数据
    fun setHintText(s: String) = setState { copy(hintText = s) }


}
