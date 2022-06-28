package com.dragon.ft_main_home.views

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.*
import com.dragon.ft_main_home.viewmodle.HomeArticle
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.simpleController

class HomeItemEpoxyFragment : BaseEpoxyFragment() {

    private val itemTabViewModel: ItemTabViewModel by fragmentViewModel()

    companion object {
        const val TYPE = "TYPE_FRAGMENT"
        fun getInstance(title: String): HomeItemEpoxyFragment {
            val fragment = HomeItemEpoxyFragment()
            fragment.arguments = Bundle().apply {
                putString(TYPE,title)
            }
            return fragment
        }
    }

    override fun requestRefresh() {
        itemTabViewModel.initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTabViewModel.onEach(deliveryMode = uniqueOnly(), prop1 = HomeArticle::isRefresh){
            setIsRefreshing(it)
        }
    }


    override fun epoxyController() = simpleController(itemTabViewModel) { state ->
        when(arguments?.getString(TYPE)){
            "测试" -> {
                if (state.dataTopArticle.isNotEmpty()){
                    state.dataTopArticle.forEachIndexed { index, topArticleBean ->
                        headerEpoxyView {
                            id("TopArticle$index")
                            author(topArticleBean.author)
                            title(topArticleBean.title)
                        }
                    }
                }

                if (state.dataBanner.isNotEmpty()){
                    bannerView {
                        id("banner")
                        addBannerLifeCycle(viewLifecycleOwner)
                        imageData(state.dataBanner)
                    }
                }
            }
        }
    }
}