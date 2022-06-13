package com.dragon.ft_main_home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.common_data.navigation.NavScreenNames
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.ft_main_home.views.dataItemView
import com.dragon.ft_main_home.views.headerView
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.simpleController
import com.dragon.module_base.databinding.FragmentBaseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_base),MavericksView {

    @Inject
    lateinit var homeProvider: HomeProvider

    private val homeViewModel: HomeViewModel by fragmentViewModel()

    private val binding by viewBinding(FragmentBaseBinding::bind)

    //控制器
    private val epoxyController by lazy { epoxyController() }

    private val navController get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.layoutManager=StickyHeaderLinearLayoutManager(requireContext())
        binding.recycleView.setController(epoxyController)
        homeViewModel.setData(mutableListOf<String>().apply {
            for (i in 1..100){
                add(i.toString())
            }
        })
        Log.e("Base", "onCreateView: 数据恢复 ${epoxyController().adapter}", )
    }

    private fun epoxyController() = simpleController(viewModel = homeViewModel,{
//        epoxyController.adapter.getModelAtPosition(it) is HeaderViewModel_
        it % 5 == 1
//        false
    }) { state ->
//        epoxyController.adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        headerView {
            id("headerView")
            //头像点击
            onAvatarClick { _, _, _, _ ->
                homeProvider.navigateToNative(NavScreenNames.MINE_PAGE)
            }
            //设置搜索词
            hintText(state.hintText)
            //搜索框点击
            onSearchLayoutClick { _, _, _, _ ->
                homeProvider.navigateToPage(id = R.id.action_main_to_search)
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
                        homeProvider.navigateToPage(id = R.id.action_main_to_search)
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

    override fun invalidate() {
        epoxyController.requestModelBuild()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}