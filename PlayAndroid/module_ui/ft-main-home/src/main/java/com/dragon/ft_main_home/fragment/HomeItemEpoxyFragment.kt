package com.dragon.ft_main_home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.airbnb.mvrx.*
import com.dragon.common_data.constant.Keys
import com.dragon.common_data.navigation.NavScreenNames
import com.dragon.ft_main_home.HomeProvider
import com.dragon.ft_main_home.helpers.carouselSnapBuilder
import com.dragon.ft_main_home.viewmodle.HomeArticle
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.ft_main_home.views.*
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.simpleController
import com.dragon.module_base.event.LoadResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 使用Epoxy
 */
@AndroidEntryPoint
class HomeItemEpoxyFragment : BaseEpoxyFragment() {

    @Inject
    lateinit var homProvider: HomeProvider


    private val itemTabViewModel: ItemTabViewModel by fragmentViewModel()

    companion object {
        const val TYPE = "TYPE_FRAGMENT"
        fun getInstance(title: String): HomeItemEpoxyFragment {
            val fragment = HomeItemEpoxyFragment()
            fragment.arguments = Bundle().apply {
                putString(TYPE, title)
            }
            return fragment
        }
    }

    override fun requestRefresh() {
        itemTabViewModel.initData()
    }

    override fun requestLoadMore() {
        itemTabViewModel.getHomeArticle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTabViewModel.onEach(
            deliveryMode = uniqueOnly(),
            prop1 = HomeArticle::isRefresh,
            prop2 = HomeArticle::isLoadMore
        ) { re, load ->
            setIsRefreshing(re)
            setIsRefreshing(load, 1)
        }
        itemTabViewModel.onAsync(
            asyncProp = HomeArticle::homeArticleData,
            deliveryMode = uniqueOnly(),{
                Log.e("出错", "onViewCreated: $it", )
            }
        ) {
            itemTabViewModel.changeVmState()
        }
    }


    override fun epoxyController() = simpleController(itemTabViewModel) { state ->
        when (arguments?.getString(TYPE)) {
            "测试" -> {
                Log.e("测试生命周期", "epoxyController:首页 ${lifecycle.currentState}")
                if (state.dataTopArticle.isNotEmpty()) {
                    state.dataTopArticle.forEachIndexed { index, topArticleBean ->
                        headerEpoxyView {
                            id("TopArticle$index")
                            author(topArticleBean.author)
                            title(topArticleBean.title)
                        }
                    }
                }

                if (state.dataBanner.isNotEmpty()) {
                    bannerPageView {
                        id("banner-page")
                        addBannerLifeCycle(lifecycle)
                        imageData(state.dataBanner)
                    }
                }

                if (state.dataOfficial.isNotEmpty()) {
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

                state.dataHomeArticle.run {
                    if (isNotEmpty()) {
                        forEachIndexed { index, homeArticleEntity ->
                            homeArticlePageView {
                                id("homearticle$index")
                                content(homeArticleEntity)
                                homeArticleClick { _, _, _, _ ->
                                    homProvider.navigateToDeepLink(activity = requireActivity(),routePath = NavScreenNames.WEB_ROUTE, args = bundleOf(Keys.URL to homeArticleEntity.link))
                                }
                            }
                        }
                    }
                }

                if (LoadResult.SUCCESS.state != state.isLoadMore) {
                    loadMoreTipView {
                        id("loadMore")
                        loadMoreState(state.isLoadMore)
                    }
                }
            }
        }
    }
}