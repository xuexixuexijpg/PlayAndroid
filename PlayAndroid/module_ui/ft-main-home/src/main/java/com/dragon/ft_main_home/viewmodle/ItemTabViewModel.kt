package com.dragon.ft_main_home.viewmodle

import android.util.Log
import com.airbnb.mvrx.*
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.dragon.ft_main_home.model.TopArticleBean
import com.dragon.ft_main_home.repo.HomeRepository
import com.drake.net.Net
import com.drake.net.utils.scopeNet
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import okhttp3.internal.wait

data class HomeArticle(
    val hasRefresh: Boolean = false,
    val data: Async<MutableList<TopArticleBean>> = Uninitialized
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
     * 获取置顶文章
     */
    fun initData() {
        if (homeData.data is Loading)return
        mRepo.getTopArticle()
            .execute {
                copy(data = it)
            }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ItemTabViewModel, HomeArticle> {
        override fun create(state: HomeArticle): ItemTabViewModel
    }

    companion object :
        MavericksViewModelFactory<ItemTabViewModel, HomeArticle> by hiltMavericksViewModelFactory()
}