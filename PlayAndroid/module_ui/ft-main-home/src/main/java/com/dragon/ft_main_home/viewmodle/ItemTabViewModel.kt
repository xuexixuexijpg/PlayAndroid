package com.dragon.ft_main_home.viewmodle

import com.airbnb.mvrx.*
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.dragon.ft_main_home.entity.BannerBean
import com.dragon.ft_main_home.entity.HomeArticleListBean
import com.dragon.ft_main_home.entity.OfficialAccountEntity
import com.dragon.ft_main_home.entity.TopArticleBean
import com.dragon.ft_main_home.repo.HomeRepository
import com.drake.net.Net
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.suspendCoroutine

/**
 * 描述视图的数据
 */
data class HomeArticle(
    val hasRefresh: Boolean = false,
    val data: Async<MutableList<TopArticleBean>> = Uninitialized,
    val bannerData: Async<MutableList<BannerBean>> = Uninitialized,
    val officialData: Async<MutableList<OfficialAccountEntity>> = Uninitialized
) :
    MavericksState

class ItemTabViewModel @AssistedInject constructor(
    @Assisted private val homeData: HomeArticle,
    private val mRepo: HomeRepository
) :
    MavericksViewModel<HomeArticle>(homeData) {

    init {
        initData()
    }

    /**
     * 获取首页初始化数据
     */
    fun initData() {
        mRepo.getTopArticle()
            .execute {
                copy(data = it)
            }
        mRepo.getBanner()
            .execute(Dispatchers.IO) {
                copy(bannerData = it)
            }
        mRepo.getOfficialAccount()
            .execute { copy(officialData = it) }
    }


    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ItemTabViewModel, HomeArticle> {
        override fun create(state: HomeArticle): ItemTabViewModel
    }

    companion object :
        MavericksViewModelFactory<ItemTabViewModel, HomeArticle> by hiltMavericksViewModelFactory()
}