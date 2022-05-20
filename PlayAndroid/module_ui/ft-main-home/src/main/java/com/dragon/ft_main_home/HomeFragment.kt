package com.dragon.ft_main_home

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.ft_main_home.views.headerView
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.simpleController
import com.dragon.service_base.navigate.NavScreenNames
import com.kpstv.navigation.AnimationDefinition
import com.kpstv.navigation.FragmentNavigator
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
            onAvatarClick { _, _, _, position ->
                Toast.makeText(requireContext(), "$position", Toast.LENGTH_SHORT).show()
            }
            //设置搜索词
            hintText(state.hintText)
            //搜索框点击
            onSearchLayoutClick { _, _, _, _ ->
                homeProvider.navigateTo(
                    NavScreenNames.SEARCH_PAGE,
                    FragmentNavigator.NavOptions(animation = AnimationDefinition.SlideInRight)
                )
            }
        }
    }
}