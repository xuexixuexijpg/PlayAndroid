package com.dragon.ft_main_home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.mvrx.*
import com.dragon.ft_main_home.helpers.carouselSnapBuilder
import com.dragon.ft_main_home.viewmodle.HomeArticle
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.ft_main_home.views.bannerPageView
import com.dragon.ft_main_home.views.headerEpoxyView
import com.dragon.ft_main_home.views.homeArticlePageView
import com.dragon.ft_main_home.views.officialAccountView
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.BaseOriginEpoxyFragment
import com.dragon.module_base.base.fragment.simpleController

/**
 * 使用Epoxy
 */
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
                Log.e("测试生命周期", "epoxyController:首页 ${lifecycle.currentState}", )
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
                    bannerPageView {
                        id("banner-page")
                        addBannerLifeCycle(lifecycle)
                        imageData(state.dataBanner)
                    }
                }

                if (state.dataOfficial.isNotEmpty()){
                    carouselSnapBuilder {
                        id("official")
                        state.dataOfficial.forEachIndexed { index, officialAccountEntity ->
                            officialAccountView {
                                id("official$index")
                                authorName(officialAccountEntity.name)
                            }
                        }
                    }
                }
                state.dataHomeArticle?.run {
                    if (datas.isNotEmpty()){
                        datas.forEachIndexed{index, homeArticleEntity ->
                            homeArticlePageView {
                                id("homearticle$index")
                                content(homeArticleEntity)
                            }
                        }
                    }
                }

            }
        }
    }
}