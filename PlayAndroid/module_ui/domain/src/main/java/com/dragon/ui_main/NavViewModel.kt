package com.dragon.ui_main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragon.common_utils.ext.FragClazz
import com.dragon.ui_main.constants.RouteConstants
import com.kpstv.navigation.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * 导航viewModel activity级别
 */
class NavViewModel : ViewModel() {
    private val navigation = MutableSharedFlow<NavigationOptions>()
    fun navigateTo(
        screen: RouteConstants,
        args: BaseArgs? = null,
        remember: Boolean = false,
        transactionType: FragmentNavigator.TransactionType = FragmentNavigator.TransactionType.REPLACE,
        animation: NavAnimation = AnimationDefinition.None,
        historyOptions: HistoryOptions = HistoryOptions.None
    ) {
        val options = NavigationOptions(
            clazz = screen.clazz,
            navOptions = FragmentNavigator.NavOptions(
                args = args,
                animation = animation,
                transaction = transactionType,
                remember = remember,
                historyOptions = historyOptions
            )
        )
        viewModelScope.launch {
            navigation.emit(options)
        }
    }

    data class NavigationOptions(
        val clazz: FragClazz,
        val navOptions: FragmentNavigator.NavOptions
    )
}