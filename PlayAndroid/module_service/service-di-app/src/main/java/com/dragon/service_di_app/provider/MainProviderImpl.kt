package com.dragon.service_di_app.provider

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main.MainProvider
import com.dragon.ft_main.di.MainScope
import com.dragon.search.SearchFragment
import com.dragon.search.SearchProvider
import com.dragon.search.di.SearchScope
import com.kpstv.navigation.FragmentNavigator
import javax.inject.Inject

//提供搜索页服务注入
class MainProviderImpl @Inject constructor(
    private val activity: FragmentActivity
) : MainProvider {
    override fun navigateTo(navOptions: FragmentNavigator.NavOptions) {
        if (activity is FragmentNavigator.Transmitter) {
            activity.getNavigator().navigateTo(SearchFragment::class, navOptions)
        } else throw IllegalArgumentException("Could not navigate")
    }
}