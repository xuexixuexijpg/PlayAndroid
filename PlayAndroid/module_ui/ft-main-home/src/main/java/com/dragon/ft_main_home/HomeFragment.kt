package com.dragon.ft_main_home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dragon.common_data.navigation.NavScreenNames
import com.dragon.common_data.navigation.NavViewModel
import com.dragon.common_data.navigation.RoutePageName
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.ft_main_home.views.HeaderViewModelBuilder
import com.dragon.ft_main_home.views.HeaderViewModel_
import com.dragon.ft_main_home.views.dataItemView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setData(mutableListOf<String>().apply {
            for (i in 1..100){
                add(i.toString())
            }
        })
    }

    override fun isSticky(): StickyHeaderLinearLayoutManager? {
        return StickyHeaderLinearLayoutManager(requireContext())
    }

    override fun epoxyController() = simpleController(viewModel = homeViewModel,{
//        epoxyController.adapter.getModelAtPosition(it) is HeaderViewModel_
        it % 5 == 0
    }) { state ->

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
                homeProvider.navigateToPage(routePath = RoutePageName.SEARCH_PAGE.path)
            }

        }

        state.data.forEachIndexed { index,s ->
            if (index%5 == 0){
                headerView {
                    id("header $index")
                    //头像点击
                    onAvatarClick { _, _, _, _ ->
                        homeProvider.navigateToNative(NavScreenNames.MINE_PAGE)
                    }
                    //设置搜索词
                    hintText(state.hintText)
                    //搜索框点击
                    onSearchLayoutClick { _, _, _, _ ->
                        homeProvider.navigateToPage(id = R.id.searchFragment)
                    }

                }
            }else{
                dataItemView {
                    id("time $index")
                    data(s)
                }
            }
        }


    }
}