package com.dragon.ft_main_home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.common_data.navigation.NavScreenNames
import com.dragon.common_data.navigation.NavViewModel
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.ft_main_home.views.headerView
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.simpleController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var homeProvider: HomeProvider

    private val homeViewModel: HomeViewModel by fragmentViewModel()

//    private val navViewModel by navGraphViewModels<NavViewModel>()

    private val navController get() = findNavController()

    override fun epoxyController() = simpleController(homeViewModel) { state ->
        headerView {
            id("header")
            //头像点击
            onAvatarClick { _, _, _, _ ->
                homeProvider.navigateToNative(NavScreenNames.MINE_PAGE)
            }
            //设置搜索词
            hintText(state.hintText)
            //搜索框点击
            onSearchLayoutClick { _, _, _, _ ->

            }
        }
    }
}