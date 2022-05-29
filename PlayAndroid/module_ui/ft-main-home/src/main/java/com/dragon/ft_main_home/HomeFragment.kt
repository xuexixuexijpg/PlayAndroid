package com.dragon.ft_main_home

import androidx.navigation.fragment.FragmentNavigator
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.ft_main_home.views.headerView
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.simpleController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var homeProvider: HomeProvider

    private val homeViewModel: HomeViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(homeViewModel) { state ->
        headerView {
            id("header")
            //头像点击
            onAvatarClick { _, _, _, _ ->

            }
            //设置搜索词
            hintText(state.hintText)
            //搜索框点击
            onSearchLayoutClick { _, _, _, _ ->

            }
        }
    }
}